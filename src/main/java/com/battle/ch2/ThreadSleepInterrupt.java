package com.battle.ch2;

import org.apache.log4j.Logger;

public class ThreadSleepInterrupt {
	private static Logger logger = Logger.getLogger(ThreadSleepInterrupt.class);
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while(true) {
					if (Thread.currentThread().isInterrupted()) {
						logger.info("t1 will exit");
						break;
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						logger.info("main interrupt t1, signal received");
						Thread.currentThread().interrupt();
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
