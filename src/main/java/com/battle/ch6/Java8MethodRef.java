package com.battle.ch6;

import java.util.ArrayList;

/**
 * NormalUser::getName  实例方法getName引用
 * System.out::println  PrintStream的实例方法println引用
 * @author jingjing03.liu
 *
 */
public class Java8MethodRef {
	public static void main(String[] args) {
		ArrayList<NormalUser> users = new ArrayList<NormalUser>();
		for (int i = 0; i < 10; i++) {
			users.add(new NormalUser(String.valueOf(i),i));
		}
		// stream()  Returns a sequential Stream with this collection as its source
		// map() Returns a stream consisting of the results of applying the given function to the elements of this stream
		// forEach() Performs an action for each element of this stream
		users.stream().map(NormalUser::getName).forEach(System.out::println);
	}
}
