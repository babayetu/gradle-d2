package com.battle.ch6;

import java.util.Arrays;
import java.util.function.IntConsumer;

import org.apache.log4j.Logger;

public class DemoFunctionalCodingOne {
	private static Logger logger = Logger.getLogger(DemoFunctionalCodingOne.class);
	private static int[] arr = {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		IntConsumer standardOut = System.out::println;
		IntConsumer errorOut = System.err::println;
		IntConsumer logOut = logger::info;
		
		Arrays.stream(arr).forEach(standardOut.andThen(errorOut).andThen(logOut));
	}
}
