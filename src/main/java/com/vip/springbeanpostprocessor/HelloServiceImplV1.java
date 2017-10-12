package com.vip.springbeanpostprocessor;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceImplV1 implements HelloService {

	@Override
	public void sayHello() {
		System.out.println("Hello from V1");

	}

	@Override
	public void sayHi() {
		System.out.println("Hi from V1");

	}

}
