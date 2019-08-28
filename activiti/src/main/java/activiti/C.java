package activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class C implements JavaDelegate{


	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub
		execution.setVariable("name", "B");
		System.out.print("C----------------");
	}

}
