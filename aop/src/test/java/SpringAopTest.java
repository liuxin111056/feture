import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.personServer;


public class SpringAopTest {

	@Test
	public void inteceptorTest() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-aop.xml");
		personServer bean = (personServer)ctx.getBean("personServiceBean");
		//bean.update();
		bean.save("badMonkey",23);
		bean.update();

//	    	personServer p = new personServerImpl();//通过new对象是不会触发aop的
//	    	p.save("11", "22");
	}
}
