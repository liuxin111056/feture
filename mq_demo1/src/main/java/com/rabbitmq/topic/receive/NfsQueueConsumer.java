package com.rabbitmq.topic.receive;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class NfsQueueConsumer implements MessageListener {
	@Override
	public void onMessage(Message message) {
		System.out.println("֪ͨǰ�ã����շǽ��ڽ���: " + new String(message.getBody()));
	}
}
