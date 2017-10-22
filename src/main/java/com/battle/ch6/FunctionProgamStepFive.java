package com.battle.ch6;

import java.util.Arrays;

/**
 * 函数式编程步步深入五: Java 8支持方法引用，因为只有一个方法，所以都不需要定义->两侧的参数和函数体
 * @author jingjing03.liu
 *
 */
public class FunctionProgamStepFive {
	static int[] arr = {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		Arrays.stream(arr).forEach(System.out::println);
	}

}
