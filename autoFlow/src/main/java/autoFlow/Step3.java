package autoFlow;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import autoFlow.core.AbstractPhaseHandle;
import autoFlow.struct.FlowRequest;

public class Step3 extends AbstractPhaseHandle {
    public Step3() {
    }

    public int handle(FlowRequest request) {
        System.out.println("step3");

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        request.setSkip_step("cardValidate");
        return -4;
    }
}
