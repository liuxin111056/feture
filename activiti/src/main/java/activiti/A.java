package activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class A implements JavaDelegate{


	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub
		execution.setVariable("days", 6);
		System.out.print("A----------------");
	}

}
