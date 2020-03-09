package autoFlow.txr;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import autoFlow.core.AbstractProcessFlow;
import autoFlow.core.PhaseHandle;
import autoFlow.struct.FlowRequest;
import autoFlow.struct.PhaseEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionTemplate;

@Service("trxProcessFlow")
public class TrxProcessFlow extends AbstractProcessFlow {
    private Logger logger = LoggerFactory.getLogger(TrxProcessFlow.class);
    @Autowired
    private PhaseEngine engine;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionAttribute transactionAttribute = new DefaultTransactionAttribute() {
        private static final long serialVersionUID = 2577132083934288181L;

        public boolean rollbackOn(Throwable ex) {
            return true;
        }
    };

    public TrxProcessFlow() {
    }

    protected FlowRequest doExecute(FlowRequest request) {
        (new TransactionTemplate(this.transactionManager, this.transactionAttribute)).execute(new TrxProcessFlow.AutoFlowTransactionCallback(request));
        return request;
    }

    private void beginStepTransaction(TransactionStatus status, TrxProcessFlow.AutoFlowThreadLocal savepoints, String key) {
        Object point = status.createSavepoint();
        ((Map)savepoints.get()).put(key, point);
    }

    private void rollbackStepTransaction(TransactionStatus status, TrxProcessFlow.AutoFlowThreadLocal savepoints, String key) {
        Object point = ((Map)savepoints.get()).get(key);
        status.rollbackToSavepoint(point);
    }

    private void saveContext(TrxProcessFlow.AutoFlowThreadLocal contextThreadLocal, String key, Object context) {
        ((Map)contextThreadLocal.get()).put(key, context);
    }

    private Object recoverContext(TrxProcessFlow.AutoFlowThreadLocal contextThreadLocal, String key) {
        Object context = ((Map)contextThreadLocal.get()).get(key);
        return context;
    }

    protected int getStepIndex(String stepId) {
        return this.engine.getIndexByStepId(stepId);
    }

    protected int fetchTotalStep(FlowRequest request) {
        return this.engine.fetchExecFlowByFlowKey(request.getFlowKey()).size();
    }

    private class AutoFlowThreadLocal extends ThreadLocal<Map<String, Object>> {
        public AutoFlowThreadLocal() {
        }
    }

    private class AutoFlowTransactionCallback extends TransactionSynchronizationAdapter implements TransactionCallback<Integer> {
        private FlowRequest request;

        public AutoFlowTransactionCallback(FlowRequest request) {
            this.request = request;
        }

        public Integer doInTransaction(TransactionStatus status) {
            List<PhaseHandle> handles = TrxProcessFlow.this.engine.fetchExecFlowByFlowKey(this.request.getFlowKey());
            PhaseHandle handle = null;
            int total = handles.size();
            TrxProcessFlow.AutoFlowThreadLocal savepoints = TrxProcessFlow.this.new AutoFlowThreadLocal();
            savepoints.set(new HashMap());
            TrxProcessFlow.AutoFlowThreadLocal contextThreadLocal = TrxProcessFlow.this.new AutoFlowThreadLocal();
            contextThreadLocal.set(new HashMap());

            while(this.request.getPhase_handler() != total) {
                try {
                    handle = (PhaseHandle)handles.get(this.request.getPhase_handler());
                } catch (Exception var13) {
                    var13.printStackTrace();
                    try {
                        throw var13;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                long st = System.currentTimeMillis();
                TrxProcessFlow.this.logger.debug("执行step [{}] 开始", handle.getClass().getName());
                TrxProcessFlow.this.beginStepTransaction(status, savepoints, handle.getClass().getName() + "_savepoint");
                TrxProcessFlow.this.saveContext(contextThreadLocal, handle.getClass().getName() + "_context", this.request.get("context"));
                int result = handle.handle(this.request);
                TrxProcessFlow.this.logger.debug("执行step [{}] 结束，用时 {}", handle.getClass().getName(), System.currentTimeMillis() - st);
                boolean flag = false;

                try {
                    flag = TrxProcessFlow.this.processResult(this.request, result);
                } catch (Exception var12) {
                    TrxProcessFlow.this.transactionAttribute.rollbackOn(var12);
                    try {
                        throw var12;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (flag) {
                    TrxProcessFlow.this.rollbackStepTransaction(status, savepoints, ((PhaseHandle)handles.get(this.request.getPhase_handler())).getClass().getName() + "_savepoint");
                    Object preContext = TrxProcessFlow.this.recoverContext(contextThreadLocal, ((PhaseHandle)handles.get(this.request.getPhase_handler())).getClass().getName() + "_context");
                    this.request.put("context", preContext);
                }
            }

            TrxProcessFlow.this.final_request(this.request);
            return 0;
        }
    }
}
