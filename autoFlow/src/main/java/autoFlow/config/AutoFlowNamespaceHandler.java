package autoFlow.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class AutoFlowNamespaceHandler extends NamespaceHandlerSupport {
    public AutoFlowNamespaceHandler() {
    }

    public void init() {
        this.registerBeanDefinitionParser("flow", new FlowBeanDefinitionParser(FlowBean.class));
        this.registerBeanDefinitionParser("step", new FlowStepBeanDefinitionParser(FlowStepBean.class));
    }
}
