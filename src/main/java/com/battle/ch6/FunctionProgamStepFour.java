package com.battle.ch6;

import java.util.Arrays;

/**
 * 函数式编程步步深入四: 函数体只有一条语句，可以省略{}
 * @author jingjing03.liu
 *
 */
public class FunctionProgamStepFour {
	static int[] arr = {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		Arrays.stream(arr).forEach((value) -> System.out.println(value));
	}

}
