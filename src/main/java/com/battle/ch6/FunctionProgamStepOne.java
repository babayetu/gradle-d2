package com.battle.ch6;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * 函数式编程步步深入一: 使用IntConsumer操作Integer流
 * IntConsumer 是 FunctionalInterface，只有accept唯一接口
 * @author jingjing03.liu
 *
 */
public class FunctionProgamStepOne {
	static int[] arr = {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		Arrays.stream(arr).forEach(new IntConsumer() {

			@Override
			public void accept(int value) {
				System.out.println(value);
			}
			
		});

	}

}
