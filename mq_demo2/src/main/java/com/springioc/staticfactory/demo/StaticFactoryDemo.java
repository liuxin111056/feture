package com.springioc.staticfactory.demo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StaticFactoryDemo {

	public static void main(String[] args) {  
		@SuppressWarnings("resource")
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("beans.xml");  
		//ʹ�þ�̬������ʽʵ����Bean  
		Speaker speaker03 = beanFactory.getBean("speaker03", Speaker.class);  
		speaker03.speech();  
	}  
}
