package com.battle.ch6;

import java.util.stream.IntStream;

public class ParallelPrimeCalculator {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		//串行执行IntStream
		System.out.println(IntStream.range(1,1000000).filter(PrimeUtil::isPrime).count());
		System.out.println((System.currentTimeMillis() - start) + "ms");
		//并行执行IntStream
		start = System.currentTimeMillis();
		System.out.println(IntStream.range(1,1000000).parallel().filter(PrimeUtil::isPrime).count());
		System.out.println((System.currentTimeMillis() - start) + "ms");
	}

}
