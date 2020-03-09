package autoFlow.struct;//

import autoFlow.context.AutoFlowContext;
import com.alibaba.fastjson.JSONObject;
import java.io.OutputStream;

public class FlowRequest {
    private int phase_handler = 0;
    private String flowKey;
    private AutoFlowContext context = new AutoFlowContext();
    private StringBuffer out_buffer;
    private String outParam;
    private String in_buffer;
    private byte[] inBuffer;
    private byte[] outBuffer;
    private OutputStream outStream;
    private String ret_code = "000000";
    private String ret_message = "成功";
    private String skip_step;
    private JSONObject jsonObject;

    public void put(String key, Object value) {
        this.context.putValue(key, value);
    }

    public Object get(String key) {
        return this.context.getValue(key);
    }

    public FlowRequest() {
    }

    public int getPhase_handler() {
        return this.phase_handler;
    }

    public void incrPhaseHandler() {
        ++this.phase_handler;
    }

    public void setPhaseHandler(int value) {
        this.phase_handler = value;
    }

    public void endFlow(int end) {
        this.phase_handler = end;
    }

    public void incPhaseHandlerByValue(int incr) {
        this.phase_handler += incr;
    }

    public void resetPhaseHandler() {
        this.phase_handler = 0;
    }

    public String getFlowKey() {
        return this.flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public StringBuffer getOut_buffer() {
        return this.out_buffer;
    }

    public void setOut_buffer(StringBuffer out_buffer) {
        this.out_buffer = out_buffer;
    }

    public OutputStream getOutStream() {
        return this.outStream;
    }

    public void setOutStream(OutputStream outStream) {
        this.outStream = outStream;
    }

    public String getIn_buffer() {
        return this.in_buffer;
    }

    public void setIn_buffer(String in_buffer) {
        this.in_buffer = in_buffer;
    }

    public String getRet_code() {
        return this.ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_message() {
        return this.ret_message;
    }

    public void setRet_message(String ret_message) {
        this.ret_message = ret_message;
    }

    public JSONObject getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getOutParam() {
        return this.outParam;
    }

    public void setOutParam(String outParam) {
        this.outParam = outParam;
    }

    public String getSkip_step() {
        return this.skip_step;
    }

    public void setSkip_step(String skip_step) {
        this.skip_step = skip_step;
    }

    public byte[] getInBuffer() {
        return this.inBuffer;
    }

    public void setInBuffer(byte[] inBuffer) {
        this.inBuffer = inBuffer;
    }

    public byte[] getOutBuffer() {
        return this.outBuffer;
    }

    public void setOutBuffer(byte[] outBuffer) {
        this.outBuffer = outBuffer;
    }
}
