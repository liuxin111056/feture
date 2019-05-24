package com.allinfinance.yak.mq.config;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.aop.TargetSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;
import org.springframework.remoting.support.RemoteInvocationResult;

import com.allinfinance.yak.mq.exception.RPCShouldRequeueException;
import com.allinfinance.yak.support.meta.RPCVersion;
import com.allinfinance.yak.support.utils.AmqpContextHolder;
import com.allinfinance.yak.support.utils.RpcLogHandler;
import com.allinfinance.yak.support.utils.TradeIdWorker;

/**
 * ����򵥵ķ�ʽʵ�ֵĻ���AMQP��RPCʵ��
 * @author licj
 *
 */
public class AmqpInvokerServiceExporter extends RemoteInvocationBasedExporter implements MessageListener, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Object proxy;
	
	private MessageConverter messageConverter = new SimpleMessageConverter();
	
	private AmqpTemplate amqpTemplate;
	
	private String rpcVersion;
	
	private RpcLogHandler rpcLogHandler=new RpcLogHandler();
	
	@Value("#{env['forceMon'] ?:false}")
	private boolean forceMon;
	
	private String serName;
	
	@Autowired
	private  Properties env;
	
	@Override
	public void onMessage(Message message) 
	{
		Object content = messageConverter.fromMessage(message);
		
		//��֤�ӿڰ汾
		String clientVersion = (String)message.getMessageProperties().getHeaders().get(AmqpInvokerClientInterceptor.RPC_VERSION_HEADER);
		
		//TODO ��Ҫ���汾�Ź�����У�飬�����Ǽ򵥵ıȽ�
		if (clientVersion != null && rpcVersion != null && clientVersion.compareTo(rpcVersion) != 0)
			logger.warn("�ӿ�[{}]�ķ���˰汾({})��ͻ��˰汾({})��һ��", getServiceInterface().getCanonicalName(), rpcVersion, clientVersion);
		
		if (content instanceof RemoteInvocation)
		{
			RemoteInvocation request = (RemoteInvocation)content;
			
			
			String curr="";
			
			String org=AmqpContextHolder.getCurrentOrg();
			
			//��ӡ���񷽷�����ǰ����־
			String msg = null;		
			
		    String AIC_SRC_TRADE_ID=AmqpContextHolder.getAicSrcTradeId();
		    
			if(forceMon){
				curr=TradeIdWorker.getInstance().getAicTraderId();
				
				// ��ӡ�����ĵ���־
				msg = String.format("TRADE_TYPE��[%s]��AIC_TRADE_ID��[%s]��AIC_SRC_TRADE_ID��[%s]", 
						serName+" "+request.getMethodName(), curr,AIC_SRC_TRADE_ID);
				rpcLogHandler.simpleOtherTradeLog(org, msg, true, true);
			}

			//����ʵ�ʷ���
			RemoteInvocationResult result = invokeAndCreateResult(request, proxy);
			
			if(forceMon){
				msg = String.format("TRADE_TYPE��[%s]��AIC_TRADE_ID��[%s]��AIC_SRC_TRADE_ID��[%s]", 
						serName+" "+request.getMethodName(), curr, AIC_SRC_TRADE_ID);
				rpcLogHandler.simpleOtherTradeLog(org, msg, false, false);
			}
			
			if (result.hasException() && result.getException() instanceof RPCShouldRequeueException)
			{
				//Ӧ��ָ����Ҫrequeue�����׳����쳣�����container��û��ָ��requeue-rejected="false"����������reject��Ϣ����requeue
				logger.warn("RPC����Ҫ��requeue����", result.getException());
				
				AmqpContextHolder.setAicSrcTradeId(null);
				throw (RPCShouldRequeueException)result.getException();
			}
			
			if (message.getMessageProperties().getHeaders().containsKey(AmqpInvokerClientInterceptor.RPC_ASYNC_HEADER))
			{
				//�첽���ã���������Ӧ������Ҫ�����Ӧ�ǲ������쳣
				if (result.getException() != null)
					logger.warn("�첽���÷����쳣", result.getException());
			}
			else
			{
				AmqpContextHolder.setAicSrcTradeId(AIC_SRC_TRADE_ID);
				//����ͬ�����õ���Ӧ
				String replyTo = message.getMessageProperties().getReplyTo();
	
				MessageProperties mp = new MessageProperties();
				//��Ӧ��Ϣ��header������Ϊ��������֧���� spring_reply_correlation֮���header
				mp.getHeaders().putAll(message.getMessageProperties().getHeaders());
				
				Message response = messageConverter.toMessage(result, mp);
	
				amqpTemplate.send(replyTo, response);
			}
			
			AmqpContextHolder.setAicSrcTradeId(null);
		}
		else
		{
			throw new IllegalArgumentException("��Ч�������" + content);
		}
	}

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	public AmqpTemplate getAmqpTemplate() {
		return amqpTemplate;
	}

	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	public boolean isForceMon() {
		return forceMon;
	}

	public void setForceMon(boolean forceMon) {
		this.forceMon = forceMon;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		proxy = getProxyForService();
		Class<?> serviceInterface = getServiceInterface();
		RPCVersion rpcVersionAnno = serviceInterface.getAnnotation(RPCVersion.class);
		if (rpcVersionAnno != null)
			rpcVersion = rpcVersionAnno.value();
		else
			logger.warn("����ӿ�[{}]û��ָ��@RPCVersion��", serviceInterface.getCanonicalName());
		
		rpcLogHandler.afterPropertiesSet();
		
		initMonService(env);
	}
	
	public  void initMonService(Properties env){
		if(env == null) return;
		
		String serName=getSerName();
		
		this.serName=serName;
		Set<Object>keys=env.keySet();
		
		for(Object obj : keys){
			if(obj !=null){
				String key=obj.toString();
				
				if(key.indexOf("monService")!=-1 && env.get(key).equals(serName)){
					this.forceMon=true;
					return;
				}
			}
		}
	}
	
	public String getSerName(){
		String serName=this.getService().getClass().getName();
		
		Class cls=this.getService().getClass();
		
		Method [] mths=cls.getMethods();
		
		for(int i=0;i<mths.length;i++){
			if(mths[i].getName().equals("getTargetSource")){
				try {
					TargetSource target=(TargetSource)mths[i].invoke(this.getService());
					String tmp=target.getTargetClass().getName();
					if(tmp!=null && !"".equals(tmp)){
						serName=tmp;
					}
					break;
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return serName;
	}
}
