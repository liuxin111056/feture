package autoFlow.struct;//



import autoFlow.config.FlowStepBean;
import autoFlow.core.PhaseHandle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service("phaseEngine")
public class PhaseEngine {
    private Map<String, List<PhaseHandle>> engine = new ConcurrentHashMap();
    private Map<String, List<PhaseHandle>> unmodify_engine = new ConcurrentHashMap();
    private Map<String, Integer> orders_inner = new HashMap();
    private Map<String, Integer> unmodify_orders = null;

    public PhaseEngine() {
    }

    public void put(String flow_key, List<PhaseHandle> handlers) {
        this.engine.put(flow_key, handlers);
        this.unmodify_engine.put(flow_key, (List)this.engine.get(flow_key));
    }

    public void putOrders(Map<FlowStepBean, Integer> orders) {
        Set<Entry<FlowStepBean, Integer>> sets = orders.entrySet();
        Iterator var4 = sets.iterator();

        while(var4.hasNext()) {
            Entry<FlowStepBean, Integer> entry = (Entry)var4.next();
            this.orders_inner.put(((FlowStepBean)entry.getKey()).getStepName(), (Integer)entry.getValue());
        }

        this.unmodify_orders = this.orders_inner;
    }

    public List<PhaseHandle> fetchExecFlowByFlowKey(String flowKey) {
        return (List)this.unmodify_engine.get(flowKey);
    }

    public int getIndexByStepId(String stepId) {
        return (Integer)this.unmodify_orders.get(stepId);
    }
}
