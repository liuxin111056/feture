package com.allinfinance.yak.mq.exception;

/**
 * RPC�������ͨ���׳�����쳣��ָ����ǰ��Ϣ����ʧ�ܣ�������Ҫrequeue���´���<br/>
 * ע�⣺container���������ָ����requeue-rejected="false"����ô���쳣��Ч��
 * @author licj
 *
 */
public class RPCShouldRequeueException extends RuntimeException {

	private static final long serialVersionUID = -4244511655234909041L;

	public RPCShouldRequeueException()
	{
		super();
	}
	
	public RPCShouldRequeueException(String message)
	{
		super(message);
	}
	
	public RPCShouldRequeueException(Throwable t)
	{
		super(t);
	}
	
	public RPCShouldRequeueException(String message, Throwable t)
	{
		super(message, t);
	}
}
