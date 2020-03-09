package autoFlow.api;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import autoFlow.struct.FlowRequest;
import autoFlow.txr.TrxProcessFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AutoFlowTrxService implements FlowService, InitializingBean, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(AutoFlowTrxService.class);
    private TrxProcessFlow trxProcessFlow;
    private transient ApplicationContext applicationContext;

    public AutoFlowTrxService() {
    }

    public String startFlow(String flowKey, byte[] request) {
        this.logger.debug("开始启动带事务的流程 {}!!!", flowKey);
        FlowRequest flowRequest = this.trxProcessFlow.execute(flowKey, request);
        this.logger.debug("带事务的流程 {}结束!!!", flowKey);
        return flowRequest.getOutParam();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        this.trxProcessFlow = (TrxProcessFlow)this.applicationContext.getBean("trxProcessFlow");
    }
}
