package com.vip.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CatController {
	@Autowired
	private CatService catService;
	
	@RequestMapping("/")
	@ResponseBody
	public void sayHello() {
		this.catService.sayHello();
	}
	
	public void sayHi() {
		this.catService.sayHi();
	}
}
