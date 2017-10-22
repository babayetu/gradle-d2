package com.battle.ch6;

import java.util.Arrays;

/**
 * 函数式编程步步深入二: 让编译器按照输入参数的类型，自动推导出
 * FunctionalInterface IntConsumer
 * 和需要重载的函数体accept()
 * @author jingjing03.liu
 *
 */
public class FunctionProgamStepTwo {
	static int[] arr = {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		Arrays.stream(arr).forEach((final int value) -> {
			System.out.println(value);
		});

	}

}
