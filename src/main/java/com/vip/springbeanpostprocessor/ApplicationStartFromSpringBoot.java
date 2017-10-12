package com.vip.springbeanpostprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 完整的演示了，由Spring Boot启动Spring容器
 * 然后按照不同的注解，决定加载哪个Impl类的方法
 * 可用于简洁的A/B功能的切换
 * 
 * 注意Impl必须要加上@Component注解，不然无法被Spring容器发现管理
 * 也就没办法用getBeansOfType()来获取了
 * @author jingjing03.liu
 *
 */
@SpringBootApplication
public class ApplicationStartFromSpringBoot {
	
	public String home() {
		return "Hello world!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStartFromSpringBoot.class, args);
	}
}
