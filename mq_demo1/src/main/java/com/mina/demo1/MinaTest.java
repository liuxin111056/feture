package com.mina.demo1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MinaTest {

	@SuppressWarnings("resource")
	public static void main(String args[]){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//		Animal a1 = context.getBean("cat", Animal.class);
//		a1.sayHello();
//		Animal a2 = context.getBean("dog", Animal.class);
//		a2.sayHello();
		
	}
}
