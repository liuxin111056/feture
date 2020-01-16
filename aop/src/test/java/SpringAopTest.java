import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.personServer;


public class SpringAopTest {

	    @Test
	    public void inteceptorTest() throws Exception{
	        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-aop.xml");
	        personServer bean = (personServer)ctx.getBean("personServiceBean");
	        bean.save("badMonkey",23);
	        
//	    	personServer p = new personServerImpl();//ͨ��new�����ǲ��ᴥ��aop��
//	    	p.save("11", "22");
	    }
}
