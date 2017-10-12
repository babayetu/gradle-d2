package com.vip.springbeanpostprocessor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@RoutingInjected
	private HelloService helloService;
	
	@RequestMapping("/")
	@ResponseBody
	public void sayHello() {
		this.helloService.sayHello();
	}
	
	public void sayHi() {
		this.helloService.sayHi();
	}
}
