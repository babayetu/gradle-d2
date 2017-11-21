package com.battle.ch6;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * 可以传给@FunctionalInterface定义的函数参数LongBinaryOperator
 * 
 * 简单来讲，就是构造一个该方法的闭包。比如：
 * Math::max等效于(a, b)->Math.max(a, b)
 * String::startWith等效于(s1, s2)->s1.startWith(s2)
 * s::isEmpty等效于()->s.isEmpty()
 * @author jingjing03.liu
 *
 */
public class LongAccumulatorDemo {

	public static void main(String[] args) throws InterruptedException {
		LongAccumulator la = new LongAccumulator(Long::max, Long.MAX_VALUE);
		
		Thread[] ts = new Thread[1000];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(() -> {
				Random random = new Random();
				long nextLong = random.nextLong();
				la.accumulate(nextLong);
			});
			ts[i].start();
		}
		
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}
		
		System.out.println(la.longValue());
	}

}
