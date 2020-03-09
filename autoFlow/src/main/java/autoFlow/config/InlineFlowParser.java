package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class InlineFlowParser extends AbstractSingleBeanDefinitionParser {
    protected static final String ID_ATTR = "id";
    protected static final String STEP_ELE = "step";
    protected static final String NEXT_ATTR = "next";
    private static final InlineStepParser stepParser = new InlineStepParser();
    private String flowName;

    public InlineFlowParser(String flowName) {
        this.flowName = flowName;
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        List<BeanDefinition> stateTransitions = new ArrayList();
        boolean stepExists = false;
        Map<String, Set<String>> reachableElementMap = new LinkedHashMap();
        String startElement = null;
        NodeList children = element.getChildNodes();
        int k = 0;

        for(int i = 0; i < children.getLength(); ++i) {
            Node node = children.item(i);
            if (node instanceof Element) {
                String nodeName = node.getLocalName();
                Element child = (Element)node;
                if (nodeName.equals("step")) {
                    BeanDefinition stepBd = stepParser.parse(child, parserContext, this.flowName);
                    if (k == 0) {
                        stepBd.getPropertyValues().add("isFirst", "Y");
                    }

                    stateTransitions.add(stepBd);
                    stepExists = true;
                    ++k;
                }
            }
        }

        if (!stepExists && !StringUtils.hasText(element.getAttribute("parent"))) {
            parserContext.getReaderContext().error("The flow [" + this.flowName + "] must contain at least one step, flow or split", element);
        }

        Set<String> allReachableElements = new HashSet();
        this.findAllReachableElements((String)startElement, reachableElementMap, allReachableElements);
        Iterator var18 = reachableElementMap.keySet().iterator();

        while(var18.hasNext()) {
            String elementId = (String)var18.next();
            if (!allReachableElements.contains(elementId)) {
                parserContext.getReaderContext().error("The element [" + elementId + "] is unreachable", element);
            }
        }

        ManagedList<BeanDefinition> managedList = new ManagedList();
        managedList.addAll(stateTransitions);
        builder.addPropertyValue("stepTransitions", managedList);
    }

    protected void findAllReachableElements(String startElement, Map<String, Set<String>> reachableElementMap, Set<String> accumulator) {
        Set<String> reachableIds = (Set)reachableElementMap.get(startElement);
        accumulator.add(startElement);
        if (reachableIds != null) {
            Iterator var6 = reachableIds.iterator();

            while(var6.hasNext()) {
                String reachable = (String)var6.next();
                if (!accumulator.contains(reachable)) {
                    this.findAllReachableElements(reachable, reachableElementMap, accumulator);
                }
            }
        }

    }

    public static Collection<BeanDefinition> getNextElements(ParserContext parserContext, String stepId, BeanDefinition stateDef, Element element) {
        Collection<BeanDefinition> list = new ArrayList();
        String shortNextAttribute = element.getAttribute("next");
        boolean hasNextAttribute = StringUtils.hasText(shortNextAttribute);
        if (hasNextAttribute) {
            list.add(getStateTransitionReference(parserContext, stateDef, (String)null, shortNextAttribute));
        }

        return list;
    }

    public static BeanDefinition getStateTransitionReference(ParserContext parserContext, BeanDefinition stateDefinition, String on, String next) {
        BeanDefinitionBuilder nextBuilder = BeanDefinitionBuilder.genericBeanDefinition("org.springframework.batch.core.job.flow.support.StateTransition");
        nextBuilder.addConstructorArgValue(stateDefinition);
        if (StringUtils.hasText(on)) {
            nextBuilder.addConstructorArgValue(on);
        }

        if (StringUtils.hasText(next)) {
            nextBuilder.setFactoryMethod("createStateTransition");
            nextBuilder.addConstructorArgValue(next);
        } else {
            nextBuilder.setFactoryMethod("createEndStateTransition");
        }

        return nextBuilder.getBeanDefinition();
    }

    protected Class<?> getBeanClass(Element element) {
        return SimpleFlow.class;
    }
}
