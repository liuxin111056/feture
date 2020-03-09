package autoFlow.core;//

import autoFlow.exception.AutoFlowException;
import autoFlow.struct.FlowRequest;


public abstract class AbstractProcessFlow implements ProcessFlow {
    public AbstractProcessFlow() {
    }

    public FlowRequest execute(String flowKey, byte[] request) {
        FlowRequest flowRrequest = this.init_request(flowKey, request);
        return this.doExecute(flowRrequest);
    }

    protected abstract FlowRequest doExecute(FlowRequest var1);

    protected abstract int getStepIndex(String var1);

    protected boolean processResult(FlowRequest request, int result) {
        boolean flag = false;
        switch(result) {
            case -6:
                request.setPhaseHandler(this.getStepIndex(request.getSkip_step()));
                break;
            case -5:
                request.resetPhaseHandler();
                flag = true;
                break;
            case -4:
                request.setPhaseHandler(this.getStepIndex(request.getSkip_step()));
                flag = true;
                break;
            case -3:
                request.endFlow(this.fetchTotalStep(request));
                break;
            case -2:
                flag = true;
                break;
            case -1:
                throw new AutoFlowException(request.getRet_message());
            case 0:
                request.incrPhaseHandler();
                break;
            default:
                throw new AutoFlowException("系统错误!!!");
        }

        return flag;
    }

    protected abstract int fetchTotalStep(FlowRequest var1);

    protected FlowRequest init_request(String flowKey, byte[] in_buffer) {
        FlowRequest request = new FlowRequest();
        request.setInBuffer(in_buffer);
        request.setFlowKey(flowKey);
        return request;
    }

    protected void final_request(FlowRequest request) {
        if (request.getOutStream() != null) {
            ;
        }
    }
}
