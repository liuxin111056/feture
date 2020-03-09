package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import autoFlow.core.PhaseHandle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FlowStepBean extends FlowStepConfig implements InitializingBean, ApplicationContextAware, BeanNameAware {
    private static final long serialVersionUID = -5291962384404971973L;
    private transient ApplicationContext applicationContext;
    private String beanName;
    private String stepName;
    private String ref;
    private String next;
    private PhaseHandle handle;
    private PhaseHandle nextHandle;
    private String desc;
    private String isFirst = "N";

    public FlowStepBean() {
    }

    public FlowStepBean(String stepName) {
        this.stepName = stepName;
    }

    public void afterPropertiesSet() throws Exception {
        this.handle = (PhaseHandle)this.applicationContext.getBean(this.ref);
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getStepName() {
        return this.stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public PhaseHandle getHandle() {
        return this.handle;
    }

    public void setHandle(PhaseHandle handle) {
        this.handle = handle;
    }

    public PhaseHandle getNextHandle() {
        return this.nextHandle;
    }

    public void setNextHandle(PhaseHandle nextHandle) {
        this.nextHandle = nextHandle;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIsFirst() {
        return this.isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }
}
