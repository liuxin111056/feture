package activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.*;

/**
 * 第一个流程运行类
 * @author yangenxiong
 *
 */
public class First {
	public static void main(String[] args)  {
		//读取配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration.
				createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine engine = config.buildProcessEngine();
		// 创建流程引擎
		//ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/First.bpmn").deploy();
		// 启动流程
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("redict", "C");
		runtimeService.startProcessInstanceByKey("process1",map);
		// 退出
		System.exit(0);
	}
}
