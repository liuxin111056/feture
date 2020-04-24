package activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

public class H implements JavaDelegate{


	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub

		System.out.println("HH----------------"+new Date());
	}

}
