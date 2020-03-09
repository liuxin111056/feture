//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package autoFlow.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutoFlowContext {
    private Map<String, Object> context = new ConcurrentHashMap();

    public AutoFlowContext() {
    }

    public void putValue(String key, Object value) {
        this.context.put(key, value);
    }

    public Object getValue(String key) {
        return this.context.get(key);
    }
}
