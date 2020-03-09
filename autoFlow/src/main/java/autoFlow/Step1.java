package autoFlow;

import autoFlow.core.AbstractPhaseHandle;
import autoFlow.struct.FlowRequest;

public class Step1 extends AbstractPhaseHandle {
    public Step1() {
    }

    public int handle(FlowRequest request) {
        System.out.println("step1");

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return 0;
    }
}
