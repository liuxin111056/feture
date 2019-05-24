package com.allinfinance.yak.mq.config;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import com.allinfinance.yak.support.meta.RPCAsync;
import com.allinfinance.yak.support.meta.RPCQueueName;
import com.allinfinance.yak.support.meta.RPCVersion;

/**
 * ����RPC����Bean
 * @author licj
 */
public class AmqpInvokerClientFactoryBean<T> extends AmqpInvokerClientInterceptor implements FactoryBean<T>, InitializingBean
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Class<?> serviceInterface;
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		
		//��ʼ��
		if (getQueueName() != null)
		{
			logger.info("ָ����Ŀ���ж���[{}]�����ǽӿڶ��塣", getQueueName());
		}
		else
		{
			RPCQueueName name = serviceInterface.getAnnotation(RPCQueueName.class);
			if (name == null)
				throw new IllegalArgumentException("�ӿ�" + serviceInterface.getCanonicalName() + "û��ָ��@RPCQueueName");
			setQueueName(name.value());
		}
		
		RPCVersion rpcVersion = serviceInterface.getAnnotation(RPCVersion.class);
		if (rpcVersion == null)
			logger.warn("�ӿ�[{}]û��ָ��@RPCVersion", serviceInterface.getCanonicalName());
		else
			setRpcVersion(rpcVersion.value());
		
		if (serviceInterface.getAnnotation(RPCAsync.class) != null)
		{
			setAsync(true);
		}
		
		//������з����Ƿ���@RPCAsync
		Set<String> names = getAsyncMethodNames();
		
		for (Method method : serviceInterface.getMethods())
		{
			boolean asyncMethod = (method.getAnnotation(RPCAsync.class) != null);
			if (asyncMethod)
			{
				names.add(method.getName());
				logger.debug("�첽����{}", method.getName());
			}
			if (!method.getReturnType().equals(void.class) &&
				(isAsync() || asyncMethod))
			{
				logger.warn("����{}�����Ϊ�첽�����䷵��ֵ��Ϊvoid��({})", method.getName(), method.getReturnType().getCanonicalName());
			}
		}
		
		if (getRpcTimeout() == -1 && getAmqpTemplate() instanceof RabbitTemplate)
		{
			//��RabbitTemplate��ȡRPC��ʱʱ��
			//ʵ�ڲ�Ը����ô�ɣ���Ϊ��ȡ��private��Ա��ͬʱ�������˶�spring-rabbit�������������ܲ��á�
			//����AMQPTempalte��RabbitTemplate������getTimeout()������ǰ���£�����Ψһ�İ취��
			//TODO ��һ�������ŵİ취
			RabbitTemplate rt = (RabbitTemplate) getAmqpTemplate();
			Long timeout = (Long) FieldUtils.readField(rt, "replyTimeout", true);
			timeout = Math.max(timeout - 5000, 1000);	//��5�봦��ʱ�䣬���1��
			logger.debug("����RPC��Ϣ��TTLΪ{}", timeout);
			setRpcTimeout(timeout);
		}
		logger.info("����RPC�ͻ��˴���ӿ�[{}]���汾[{}]��Ŀ�����[{}]��", serviceInterface.getCanonicalName(), getRpcVersion(), getQueueName());
		
		return (T)Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{serviceInterface}, this);
	}

	@Override
	public Class<?> getObjectType() {
		return serviceInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Class<?> getServiceInterface() {
		return serviceInterface;
	}
	@Required
	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//��������������˸ø��࣬���Ի����뱣���÷��������Ժ�gmp�����µ�1.0.6�Ժ�ſ����õ�����ȫ��getObject����
	}
}
