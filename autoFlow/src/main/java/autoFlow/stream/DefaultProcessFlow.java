package autoFlow.stream;

import autoFlow.core.AbstractProcessFlow;
import autoFlow.core.PhaseHandle;
import autoFlow.exception.AutoFlowException;
import autoFlow.struct.FlowRequest;
import autoFlow.struct.PhaseEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("defaultProcessFlow")
public class DefaultProcessFlow extends AbstractProcessFlow {
    @Autowired
    private PhaseEngine engine;

    public DefaultProcessFlow() {
    }

    protected FlowRequest doExecute(FlowRequest request) {
        List<PhaseHandle> handles = this.engine.fetchExecFlowByFlowKey(request.getFlowKey());
        PhaseHandle handle = null;
        int total = handles.size();

        while(request.getPhase_handler() != total) {
            handle = (PhaseHandle)handles.get(request.getPhase_handler());
            int result = handle.handle(request);

            try {
                this.processResult(request, result);
            } catch (AutoFlowException var7) {
                this.final_request(request);
                return request;
            }
        }

        this.final_request(request);
        return request;
    }

    protected int getStepIndex(String stepId) {
        return this.engine.getIndexByStepId(stepId);
    }

    protected int fetchTotalStep(FlowRequest request) {
        return this.engine.fetchExecFlowByFlowKey(request.getFlowKey()).size();
    }
}
