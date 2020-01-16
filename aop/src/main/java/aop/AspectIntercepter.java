package aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import service.Person;

@Aspect//��������һ������
@Component//��������һ���������ָ...����ȥ��
public class AspectIntercepter {

	  @Pointcut(value="execution(* service.personServerImpl.save(..))")
	  private void pointCut(){//����һ������� �����ֱ֪ͨ����������㷽��pointCut����            personServerImpl��������з���
	  }

	   //����֪ͨ�����ӵ�����㿪ʼִ�У���һ������ǰ��֪ͨ������һ������ִ�в���������
	    @Around(value="pointCut()")
	    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
	        System.out.println("@Around���뻷��֪ͨ...");
	        Object object = pjp.proceed();//ִ�и÷���
	        System.out.println(pjp.getThis()+"  �����������˳�����;����[@Around]������...");
	      return object;

	    }

	    //ǰ��֪ͨ�����뻷�ƺ�ִ�У���һ��ִ�з�����
	    @Before(value="pointCut()")
	    public void doAccessCheck(JoinPoint joinPoint){
	        System.out.println("@Beforeǰ��֪ͨ:"+Arrays.toString(joinPoint.getArgs()));
	    }

	    //�쳣֪ͨ������ʱִ�У�
	    @AfterThrowing(value="pointCut()",throwing="ex")
	    public void doAfterThrow(JoinPoint joinPoint,Throwable ex){
	        System.out.println("@AfterThrowing����֪ͨ(�쳣֪ͨ)"+Arrays.toString(joinPoint.getArgs()));
	        System.out.println("@AfterThrowing�쳣��Ϣ��"+ex);
	    }

	    //����֪ͨ(����֮ǰִ��)
	    @After(value="pointCut()")
	    public void after(){
	    	System.out.println("@After����֪ͨ...");
	    }

	    //����֪ͨ����������֪ͨ�����ִ�У�
	    @AfterReturning(value="pointCut()")
	    public void doAfter(){
	        System.out.println("@AfterReturning����֪ͨ...End!");
	    }
	@AfterReturning(returning="rvt", value="pointCut()")
	public Object AfterExec(JoinPoint joinPoint,Object rvt){
		//pointcut�Ƕ�Ӧ��ע����   rvt���Ƿ���������֮��Ҫ���ص�ֵ
		Person pp=(Person)rvt;
		System.out.println("AfterReturning��ǿ����ȡĿ�귽���ķ���ֵ��" + pp.getName());
		return rvt;
	}
}
