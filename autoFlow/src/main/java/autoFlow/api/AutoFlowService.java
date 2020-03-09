package autoFlow.api;//
import java.nio.charset.Charset;
import autoFlow.stream.DefaultProcessFlow;
import autoFlow.struct.FlowRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

public class AutoFlowService implements FlowService, InitializingBean, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(AutoFlowService.class);
    private DefaultProcessFlow defaultProcessFlow;
    private transient ApplicationContext applicationContext;

    public AutoFlowService() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        this.defaultProcessFlow = (DefaultProcessFlow)this.applicationContext.getBean("defaultProcessFlow");
    }

    public String startFlow(String flowKey, byte[] request) {
        this.logger.debug("启动不带事务的流程 {}!!!", flowKey);
        FlowRequest flowRequest = this.defaultProcessFlow.execute(flowKey, request);
        this.logger.debug("不带事务的流程 {}结束!!!", flowKey);
        return new String(flowRequest.getOutBuffer(), Charset.forName("UTF-8"));
    }
}
