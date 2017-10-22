package com.battle.ch6;

import java.util.Arrays;

/**
 * 函数式编程步步深入三: FunctionalInterface IntConsumer接口的参数类型也是可以省略的
 * 因为必然是int
 * @author jingjing03.liu
 *
 */
public class FunctionProgamStepThree {
	static int[] arr = {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		Arrays.stream(arr).forEach((value) -> {
			System.out.println(value);
		});

	}

}
