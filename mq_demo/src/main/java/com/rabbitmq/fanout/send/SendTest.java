package com.rabbitmq.fanout.send;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

public class SendTest {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq-fanout-send.xml");
		RabbitTemplate rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);
		//������Ϊleo.pay.fanout.exchange��·�����淢�����ݣ��ͻ�����ֻҪ�����·�ɰ���һ��Ķ��ж����յ������Ϣ��������ȫƵ�㲥�����Ͷ˲��ܶ�����˭�����ɿͻ����Լ�ȥ�󶨣�˭��Ҫ����˭ȥ���Լ��Ĵ�����С�
		for(int i = 1; i <= 1; i++) {
			
			String str = "ͨ���ǽ��ڽ��׵�,���磺��Ƭ����" + i;
			System.out.println("�����߷��ͣ�"+ str);
			//�ѡ�nfs.meituan.notice�� Ϊrouting key�����͵������ϡ�
			NoticeRequest head = new NoticeRequest();
			head.setOrg("00000000001");
			head.setServiceSn("111100001");
			head.setServiceId("003");
			head.setChannelId("93");
			head.setRequestTime(new Date());
			head.setServiceCode("S10010");
			BodyDemo demo = new BodyDemo();
			demo.setCardNo("625000000000");
			String requestInfoString = getJsonStringByObject(head, demo);
			
			rabbitTemplate.send("fanoutExchange", "", new Message(requestInfoString.getBytes(), new MessageProperties()));
		}
		
//		for(int i = 1; i <= 1; i++) {
//			
//			String str = "ͨ�����ڽ��׵�,���磺ȡ�ֽ���" + i;
//			System.out.println("�����߷��ͣ�"+ str);
//			//�ѡ�nfs.meituan.notice�� Ϊrouting key�����͵������ϡ�
//			rabbitTemplate.send("fanoutExchange", "", new Message(str.getBytes(), new MessageProperties()));
//		}
	
	}
//	@Test
//	public void testRabbitMq() throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//		RabbitTemplate rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);
//		//������Ϊleo.pay.fanout.exchange��·�����淢�����ݣ��ͻ�����ֻҪ�����·�ɰ���һ��Ķ��ж����յ������Ϣ��������ȫƵ�㲥�����Ͷ˲��ܶ�����˭�����ɿͻ����Լ�ȥ�󶨣�˭��Ҫ����˭ȥ���Լ��Ĵ�����С�
//		for(int i = 1; i <= 10; i++) {
//			String str = "hello" + i;
//			rabbitTemplate.send("noticeExchange", "", new Message(str.getBytes(), new MessageProperties()));
//		}
//	}

	private static String getJsonStringByObject(NoticeRequest head, BodyDemo demo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(NoticeRequest.HEAD, JSON.toJSONString(head));
		map.put(NoticeRequest.BODY, JSON.toJSONString(demo));
		String requestInfoString = JSON.toJSONString(map);
		return requestInfoString;
	}
}
