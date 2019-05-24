package com.springioc.instancefactory.demo;

public class AnimalFactory {

	public Animal getAnimal(String type){  //���������ȥ����static�ؼ���
		if ("cat".equalsIgnoreCase(type)){
			return new Cat();
		} else {
			return new Dog();
		}
	}
}
