package com.vip.springbeanpostprocessor;

@RoutingSwitch("hello.switch")
public interface HelloService {
	@RoutingSwitch("B")
	void sayHello();
	void sayHi();
}
