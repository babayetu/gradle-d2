package com.vip.springbeanpostprocessor;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceImplV2 implements HelloService {

	@Override
	public void sayHello() {
		System.out.println("Hello from V2");
	}

	@Override
	public void sayHi() {
		System.out.println("Hi from V2");
	}
}
