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

@Aspect//声明这是一个切面
@Component//声明这是一个组件，泛指...可以去掉
public class AspectIntercepter {

	@Pointcut(value="execution(* service.personServerImpl.*(..))")
	private void pointCut(){//定义一个切入点 后面的通知直接引入切入点方法pointCut即可            personServerImpl下面的所有方法
	}

	//环绕通知（连接到切入点开始执行，下一步进入前置通知，在下一步才是执行操作方法）
	/*@Around("execution(* service..*.*(..)) && @annotation(exceptionCatch)")
	public Object doBasicProfiling(ProceedingJoinPoint pjp, ExceptionCatch exceptionCatch) throws Throwable{
		System.out.println("@Around进入环绕通知...");
		Object object = pjp.proceed();//执行该方法
		System.out.println(pjp.getThis()+"  操作结束，退出方法;环绕[@Around]结束！...");
		return object;

	}*/

	//异常通知（出错时执行） 通过注解实现
	@AfterThrowing("execution(* service..*.*(..)) && @annotation(exceptionCatch)")
	public void doAfterThrow(JoinPoint joinPoint,ExceptionCatch exceptionCatch){
		System.out.println("@AfterThrowing例外通知(异常通知)"+Arrays.toString(joinPoint.getArgs()));
		System.out.println("@AfterThrowing异常信息："+exceptionCatch);
	}



	//前置通知（进入环绕后执行，下一步执行方法）
	@Before("execution(* service..*.*(..)) && @annotation(ExceptionCatch)")
	public void doAccessCheck(JoinPoint joinPoint){
		System.out.println("@Before前置通知:"+Arrays.toString(joinPoint.getArgs()));
	}

	//后置通知(返回之前执行)
	@After("execution(* service..*.*(..)) && @annotation(ExceptionCatch)")
	public void after(){
		System.out.println("@After后置通知...");
	}

	@AfterReturning(returning="rvt", value="execution(* service..*.*(..)) && @annotation(ExceptionCatch)")
	public Object AfterExec(JoinPoint joinPoint,Object rvt){
		//pointcut是对应的注解类   rvt就是方法运行完之后要返回的值
		Person pp=(Person)rvt;
		System.out.println("AfterReturning增强：获取目标方法的返回值：" + pp.getName());
		return rvt;
	}

/*
	//异常通知（出错时执行）
	@AfterThrowing(value="pointCut()",throwing="ex")
	public void doAfterThrow(JoinPoint joinPoint,Throwable ex){
		System.out.println("@AfterThrowing例外通知(异常通知)"+Arrays.toString(joinPoint.getArgs()));
		System.out.println("@AfterThrowing异常信息："+ex);
	}



	//最终通知（正常返回通知，最后执行）
	@AfterReturning(value="pointCut()")
	public void doAfter(){
		System.out.println("@AfterReturning最终通知...End!");
	}
	*/
}
