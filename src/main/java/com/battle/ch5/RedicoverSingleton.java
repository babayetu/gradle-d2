package com.battle.ch5;

/**
 * 第一次引用RediscoverSingleton是触发这个类实例的创建
 * @author jingjing03.liu
 *
 */
public class RedicoverSingleton {
	public static int STATUS = 1;
	private RedicoverSingleton() {
		System.out.println("single is created");
	}
	private static RedicoverSingleton instance = new RedicoverSingleton();
	
	public RedicoverSingleton getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		System.out.println(STATUS);
	}
}
