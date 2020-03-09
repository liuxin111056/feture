import autoFlow.api.AutoFlowTrxService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("service-context.xml");
        System.out.println("=====启动成功=====");
        AutoFlowTrxService start = (AutoFlowTrxService)context.getBean("autoFlowService");
        String ret = start.startFlow("fdpJob", "test".getBytes());
        System.out.println(ret);
    }
}
