package com.rabbitmq.fanout.receive;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class FsQueueConsumer implements MessageListener {
	@Override
	public void onMessage(Message message) {
		System.out.println("����թϵͳ�����ս��ڽ���: " + new String(message.getBody()));
	}
}
