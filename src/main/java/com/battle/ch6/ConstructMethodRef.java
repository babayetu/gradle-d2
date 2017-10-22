package com.battle.ch6;

import java.util.ArrayList;
/**
 * 演示构造函数自动绑定的情况
 * @author jingjing03.liu
 *
 */
public class ConstructMethodRef {
	@FunctionalInterface
	interface UserFactory<U extends NormalUser> {
		U create(String name, int age);
	}
	
	static UserFactory<NormalUser> uf = NormalUser::new;
	
	public static void main(String[] args) {
		ArrayList<NormalUser> users = new ArrayList<NormalUser>();
		for (int i = 0; i < 10; i++) {
			users.add(uf.create("User" + String.valueOf(i), i));
		}
		
		users.stream().map(NormalUser::getName).forEach(System.out::println);
	}
}
