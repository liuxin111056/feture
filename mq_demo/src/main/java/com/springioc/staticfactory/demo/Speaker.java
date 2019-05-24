package com.springioc.staticfactory.demo;

/** 
 * ��̬������ 
 * 
 */  
public class Speaker {  

	private String name ;  
	private String topic;  
	private int timeHour;  

	private static Speaker speaker = null;  

	private Speaker() {  
		this.name = "Jacke";  
		this.topic = "play football!";  
		this.timeHour = 2;  
	}

	/** 
	 * �������� 
	 * @return 
	 */  
	public static Speaker getInstance() {  

		if(null==speaker){  
			speaker = new Speaker();  
		}  
		return speaker;  
	}  

	/** 
	 * �ݽ� 
	 */  
	public void speech() {  
		System.out.println(toString());  
	}  


	@Override  
	public String toString() {  
		return "Speaker [name=" + name + ", topic=" + topic + ", timeHour="  
				+ timeHour + "]";  
	}  
}  