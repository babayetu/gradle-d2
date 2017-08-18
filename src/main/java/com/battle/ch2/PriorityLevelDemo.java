package com.battle.ch2;

import org.apache.log4j.Logger;

public class PriorityLevelDemo {
	private static Logger logger = Logger.getLogger(PriorityLevelDemo.class);
	
	public static class LowPriority extends Thread {
		@Override
		public void run() {
			int count = 0;
			while(true) {
				synchronized(PriorityLevelDemo.class) {
					count++;
					if (count>10000000) {
						logger.info("low priority completed");
						break;
					}
				}
			}
		}
	}
	
	public static class HighPriority extends Thread {
		@Override
		public void run() {
			int count = 0;
			while(true) {
				synchronized(PriorityLevelDemo.class) {
					count++;
					if (count>10000000) {
						logger.info("high priority completed");
						break;
					}
				}
			}
		}		
	}
	
	public static void main(String[] args) {
		Thread low = new LowPriority();
		Thread high = new HighPriority();
		low.setPriority(Thread.MIN_PRIORITY);
		high.setPriority(Thread.MAX_PRIORITY);
		low.start();
		high.start();
	}

}
