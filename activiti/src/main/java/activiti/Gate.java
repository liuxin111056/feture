package activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;

public class Gate implements ExecutionListener {

	public void notify(DelegateExecution delegateExecution) {
		// TODO Auto-generated method stub
		System.out.println("Gate----------------");
		delegateExecution.setVariable("redict", "G");
	}
}
