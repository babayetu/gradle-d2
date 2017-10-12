package com.vip.springdemo;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CatServiceImpl implements CatService{
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public void sayHello() {
		Map<String, CatService> bb = applicationContext.getBeansOfType(CatService.class);
		System.out.println(bb.size());
		
		if (bb.size() > 0) {
			for (Entry<String, CatService> entry : bb.entrySet()) {
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
		}
	}

	@Override
	public void sayHi() {
		System.out.println("hi");	
	}

}
