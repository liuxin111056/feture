package autoFlow;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import autoFlow.core.AbstractPhaseHandle;
import autoFlow.struct.FlowRequest;

public class Step2 extends AbstractPhaseHandle {
    public Step2() {
    }

    public int handle(FlowRequest request) {
        System.out.println("step2");

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return 0;
    }
}
