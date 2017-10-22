package com.battle.ch6;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 用Function定义lamda函数，用lamda函数的apply来调用
 * @author jingjing03.liu
 *
 */
public class LamdaDemo {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
		//lamda样子1
		numbers.forEach((Integer value) -> System.out.println(value));

		final int num = 2;
		//lamda样子2
		Function<Integer,Integer> convertor = (from) -> from * num;
		System.out.println(convertor.apply(3));
	}
}
