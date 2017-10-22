package com.battle.ch6;

import java.util.Arrays;

/**
 * 演示重点：函数式map调用，不会改变外面输入对象的值
 * 也就是arr的值是没有改变的
 * @author jingjing03.liu
 *
 */
public class ImmutableFuncProgram {

	public static void main(String[] args) {
		int[] arr = {1,2,3,4,5,6,7,8,9};
		Arrays.stream(arr).map((x) -> x=x+1).forEach(System.out::println);
		System.out.println();
		Arrays.stream(arr).forEach(System.out::println);
	}

}
