package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class FlowStepBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<FlowStepBean> beanClass;

    public FlowStepBeanDefinitionParser(Class<FlowStepBean> beanClass) {
        this.beanClass = beanClass;
    }

    protected Class<FlowStepBean> getBeanClass(Element element) {
        return this.beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext context) {
        String id = element.getAttribute("id");
        if (context.getRegistry().containsBeanDefinition(id)) {
            throw new IllegalStateException("Duplicate spring bean id " + id);
        } else {
            String next_step = element.getAttribute("next");
            if (StringUtils.isEmpty(next_step)) {
                throw new IllegalStateException("next attribute not be null ");
            } else {
                if (StringUtils.isEmpty(id)) {
                    id = this.beanClass.getSimpleName();
                }

                RootBeanDefinition beanDefinition = new RootBeanDefinition();
                beanDefinition.setBeanClass(this.beanClass);
                beanDefinition.getPropertyValues().addPropertyValue("next", next_step);
                context.getRegistry().registerBeanDefinition(id, beanDefinition);
                return beanDefinition;
            }
        }
    }
}
