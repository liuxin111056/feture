package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class InlineStepParser {
    private static final String ID_ATTR = "id";
    private static final String SERVICE_REF = "ref";
    private String flowName;

    public InlineStepParser(String flowName) {
        this.flowName = flowName;
    }

    public InlineStepParser() {
    }

    public BeanDefinition parse(Element element, ParserContext parserContext, String jobFactoryRef) {
        BeanDefinitionBuilder stateBuilder = BeanDefinitionBuilder.genericBeanDefinition(FlowStepBean.class);
        String stepId = element.getAttribute("id");
        AbstractBeanDefinition bd = this.parseStep(element, parserContext, jobFactoryRef);
        parserContext.registerBeanComponent(new BeanComponentDefinition(bd, stepId));
        stateBuilder.addConstructorArgReference(stepId);
        return bd;
    }

    protected AbstractBeanDefinition parseStep(Element stepElement, ParserContext context, String jobFactoryRef) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(FlowStepBean.class);
        AbstractBeanDefinition bd = builder.getRawBeanDefinition();
        String stepName = stepElement.getAttribute("id");
        if (context.getRegistry().containsBeanDefinition(stepName)) {
            throw new IllegalStateException("Duplicate spring step key " + stepName);
        } else {
            builder.addConstructorArgValue(stepName);
            String serviceRef = stepElement.getAttribute("ref");
            if (StringUtils.hasText(serviceRef)) {
                bd.getPropertyValues().add("ref", serviceRef);
            }

            String next = stepElement.getAttribute("next");
            if (StringUtils.hasText(next)) {
                bd.getPropertyValues().add("next", next);
            }

            String desc = stepElement.getAttribute("desc");
            if (StringUtils.hasText(desc)) {
                bd.getPropertyValues().add("desc", desc);
            }

            return bd;
        }
    }
}
