package com.rabbitmq.fanout.publisher;

import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SendTest {

	@Test
	public void testRabbitMq() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		RabbitTemplate rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);
//		RabbitTemplate rabbitTemplate = (RabbitTemplate) LeoContext.getContext().getApplication().getBean("rabbitTemplate");
		//������Ϊleo.pay.fanout.exchange��·�����淢�����ݣ��ͻ�����ֻҪ�����·�ɰ���һ��Ķ��ж����յ������Ϣ��������ȫƵ�㲥�����Ͷ˲��ܶ�����˭�����ɿͻ����Լ�ȥ�󶨣�˭��Ҫ����˭ȥ���Լ��Ĵ�����С�
		for(int i = 1; i <= 10; i++) {
			String str = "hello" + i;
			rabbitTemplate.send("leo.pay.fanout.exchange", "", new Message(str.getBytes(), new MessageProperties()));
		}
	}
}
