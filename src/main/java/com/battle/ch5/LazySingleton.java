package com.battle.ch5;

/**
 * 延迟创建Singleton实例，为了防止被多次创建，getInstance()需要加上synchronized参数
 * @author jingjing03.liu
 *
 */
public class LazySingleton {
	private LazySingleton() {
		System.out.println("single is created");
	}
	private static LazySingleton instance = null;
	
	public static synchronized LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		
	}
}
