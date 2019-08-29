package activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class B implements JavaDelegate {



	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub
		execution.setVariable("redict", "E");
		System.out.println("B-----------------------");
	}

}
