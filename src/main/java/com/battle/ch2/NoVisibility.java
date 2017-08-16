package com.battle.ch2;

import org.apache.log4j.Logger;

/**
 * 演示重点，如果ready不加volatile，while循环可能永远无法退出
 * 虚拟机client模式，和server模式，JIT优化结果的不同
 * @author jingjing03.liu
 *
 */
public class NoVisibility {
	private static volatile boolean ready;
//	private static boolean ready;
	private static int number;

	private static Logger logger = Logger.getLogger(NoVisibility.class);

	private static class ReadThread extends Thread {
		@Override
		public void run() {
			while (!ready);  //可能永远无法退出
			logger.info(number);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new ReadThread().start();
		Thread.sleep(1000);
		number = 42;
		ready = true;
		Thread.sleep(1000);
	}
}
