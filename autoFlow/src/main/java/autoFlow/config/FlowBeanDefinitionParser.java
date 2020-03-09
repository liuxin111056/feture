package autoFlow.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class FlowBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    private final Class<FlowBean> beanClass;

    public FlowBeanDefinitionParser(Class<FlowBean> beanClass) {
        this.beanClass = beanClass;
    }

    protected Class<FlowBean> getBeanClass(Element element) {
        return this.beanClass;
    }

    protected void doParse(Element element, ParserContext context, BeanDefinitionBuilder builder) {
        String flowName = element.getAttribute("id");
        if (context.getRegistry().containsBeanDefinition(flowName)) {
            throw new IllegalStateException("Duplicate spring flow key " + flowName);
        } else {
            builder.addConstructorArgValue(flowName);
            String desc = element.getAttribute("desc");
            builder.addPropertyValue("desc", desc);
            InlineFlowParser flow = new InlineFlowParser(flowName);
            BeanDefinition flowDef = flow.parse(element, context);
            builder.addPropertyValue("flow", flowDef);
        }
    }
}
