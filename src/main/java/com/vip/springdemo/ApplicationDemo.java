package com.vip.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationDemo {

	public String home() {
		return "Hello world!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationDemo.class, args);
	}

}
