package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import autoFlow.core.PhaseHandle;
import autoFlow.exception.AutoFlowException;
import autoFlow.struct.PhaseEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FlowBean extends FlowConfig implements InitializingBean, ApplicationContextAware {
    private static final long serialVersionUID = -4415114537023582986L;
    private transient ApplicationContext applicationContext;
    private String flowName;
    private String desc;
    private boolean initialed = false;
    private Map<FlowStepBean, Integer> orders = new HashMap();
    private Map<String, List<PhaseHandle>> flows = new HashMap();
    private SimpleFlow flow;
    private static final String TRUE_FLAG = "Y";

    public FlowBean(String flowName) {
        this.setFlowName(flowName);
    }

    public void afterPropertiesSet() throws Exception {
        if (!this.initialed) {
            this.initial();
        }

        PhaseEngine phaseEngine = (PhaseEngine)this.applicationContext.getBean("phaseEngine");
        phaseEngine.put(this.flowName, (List)this.flows.get(this.flowName));
        phaseEngine.putOrders(this.orders);
    }

    private void initial() {
        List<PhaseHandle> lists = new ArrayList();
        FlowStepBean stepBean = this.findStartStep();
        int var3 = 0;

        while(stepBean != null) {
            lists.add(stepBean.getHandle());
            this.orders.put(stepBean, var3++);
            if (StringUtils.isNotEmpty(stepBean.getNext())) {
                stepBean = (FlowStepBean)this.applicationContext.getBean(stepBean.getNext());
            } else {
                stepBean = null;
            }
        }

        this.flows.put(this.flowName, lists);
    }

    private FlowStepBean findStartStep() {
        List<FlowStepBean> lists = this.flow.getStepTransitions();
        Iterator var3 = lists.iterator();

        while(var3.hasNext()) {
            FlowStepBean bean = (FlowStepBean)var3.next();
            if ("Y".equals(bean.getIsFirst())) {
                return bean;
            }
        }

        throw new AutoFlowException("系统错误!!!");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getFlowName() {
        return this.flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public SimpleFlow getFlow() {
        return this.flow;
    }

    public void setFlow(SimpleFlow flow) {
        this.flow = flow;
    }
}
