package autoFlow.config;//


import java.util.ArrayList;
import java.util.List;

public class SimpleFlow {
    private List<FlowStepBean> stepTransitions = new ArrayList();

    public SimpleFlow() {
    }

    public List<FlowStepBean> getStepTransitions() {
        return this.stepTransitions;
    }

    public void setStepTransitions(List<FlowStepBean> stepTransitions) {
        this.stepTransitions = stepTransitions;
    }
}
