package com.battle.ch2;

import org.apache.log4j.Logger;

public class BadSuspend {
	private static Logger logger = Logger.getLogger(BadSuspend.class);
	
	private static Object o = new Object();
	
	private static class ChangeObjectThread extends Thread {
		
		public ChangeObjectThread(String paramString) {
			super(paramString);
		}

		@Override
		public void run() {
			synchronized(o) {
				logger.info("in "+getName());
				Thread.currentThread().suspend();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new ChangeObjectThread("t1");
		Thread t2 = new ChangeObjectThread("t2");
		t1.start();
		Thread.sleep(200);
		t2.start();
		t1.resume();
		t2.resume();
		t1.join();
		t2.join();
	}
}
