package com.battle.ch2;

import org.apache.log4j.Logger;

public class ThreadInterruptDemo {
	private static Logger logger = Logger.getLogger(ThreadInterruptDemo.class);
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while(true) {
					if (Thread.currentThread().isInterrupted()) {
						logger.info("t1 thread is interrupted");
						break;
					}
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000);
		t1.interrupt();
	}
}
