package com.allinfinance.yak.mq.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

public class AmqpInvokerClientInterceptor implements InvocationHandler {
	
	public final static String RPC_VERSION_HEADER = "yak.rpc.version";
	
	public final static String RPC_ASYNC_HEADER = "yak.async";
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private DestinationResolver destinationResolver;

	private String queueName;
	
	private AmqpTemplate amqpTemplate;
	
	private boolean async = false;
	
	private Set<String> asyncMethodNames = new HashSet<String>();
	
	private long rpcTimeout = -1;
	
	/**
	 * �汨�ķ��͵Ľӿڰ汾��Ϣ
	 */
	private String rpcVersion;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		RemoteInvocation remoteInvocation = new RemoteInvocation(method.getName(), method.getParameterTypes(), args);
		String actualQueueName;
		if (destinationResolver != null)
			actualQueueName = destinationResolver.getActualQueueName(queueName);
		else
			actualQueueName = queueName;

		if (isAsync() || asyncMethodNames.contains(method.getName()))
		{
			if (logger.isDebugEnabled())
				logger.debug("�����첽��Ϣ��[{}]��������Ϊ[{}]", actualQueueName, method.getName());
			//�첽��ʽʹ�ã�ͬʱҪ��֪����˲�Ҫ������Ӧ
			amqpTemplate.convertAndSend(actualQueueName, remoteInvocation, new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					//�Ѱ汾������Ϣͷ��
					message.getMessageProperties().setHeader(RPC_VERSION_HEADER, rpcVersion);
					//ָ����Ҫ����Ӧ
					message.getMessageProperties().setHeader(RPC_ASYNC_HEADER, "*");
					return message;
				}
			});
			return null;
		}
		else
		{
			//ͬ�����ã�Ҫ����Ӧ��
			if (logger.isDebugEnabled())
				logger.debug("����ͬ����Ϣ��[{}]��������Ϊ[{}]", actualQueueName, method.getName());
			Object response = amqpTemplate.convertSendAndReceive(actualQueueName, remoteInvocation, new MessagePostProcessor() {
				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					MessageProperties props = message.getMessageProperties();
					//�Ѱ汾������Ϣͷ��
					props.setHeader(RPC_VERSION_HEADER, rpcVersion);
					if (rpcTimeout != -1)
					{
						props.setExpiration(String.valueOf(rpcTimeout));
						logger.debug("����RPC��Ϣ��TTLΪ{}", rpcTimeout);
					}
					return message;
				}
			});
			
			if (response == null)
			{
				logger.error("RPC������Ӧ��ʱ[{}]", actualQueueName);
				throw new IllegalArgumentException("RPC������Ӧ��ʱ:" + actualQueueName);
			}
			if (response instanceof RemoteInvocationResult)
				return ((RemoteInvocationResult)response).recreate();
			else
				throw new IllegalArgumentException("��Ч���ض���:" + response); 
		}
	}

	public AmqpTemplate getAmqpTemplate() {
		return amqpTemplate;
	}
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public DestinationResolver getDestinationResolver() {
		return destinationResolver;
	}

	public void setDestinationResolver(DestinationResolver destinationResolver) {
		this.destinationResolver = destinationResolver;
	}

	public String getRpcVersion() {
		return rpcVersion;
	}

	public void setRpcVersion(String rpcVersion) {
		this.rpcVersion = rpcVersion;
	}

	public boolean isAsync() {
		return async;
	}

	/**
	 * �첽��ʽʹ�ã�����������Ӧ���ġ�
	 * @param async
	 */
	public void setAsync(boolean async) {
		this.async = async;
	}

	public Set<String> getAsyncMethodNames() {
		return asyncMethodNames;
	}

	public void setAsyncMethodNames(Set<String> asyncMethodNames) {
		this.asyncMethodNames = asyncMethodNames;
	}

	public long getRpcTimeout() {
		return rpcTimeout;
	}

	public void setRpcTimeout(long rpcTimeout) {
		this.rpcTimeout = rpcTimeout;
	}
}
