package com.battle.ch2;

import org.apache.log4j.Logger;

public class VolatileNotAtomic {
	private static Logger logger = Logger.getLogger(VolatileNotAtomic.class);
	
	private volatile static int count = 0;
	
	private static class PlusTask implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				count++;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] tarray = new Thread[10];
		
		for (int i = 0; i < 10; i++) {
			tarray[i]= new Thread(new PlusTask());
			tarray[i].start();
		}
		
		for (int i = 0; i < 10; i++) {
			tarray[i].join();
		}
		
		logger.info("final result: " + count);
	}
}
