package com.battle.ch3;

import java.util.concurrent.locks.LockSupport;

import org.apache.log4j.Logger;

public class LockSupportDemo {
	private static Logger logger = Logger.getLogger(LockSupportDemo.class);
	public static Object u = new Object();
	static ChangeObjectThread t1 = new ChangeObjectThread("thread t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("thread t2");
	
	public static class ChangeObjectThread extends Thread {

		public ChangeObjectThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			synchronized(u) {
				logger.info("in:" + getName());
				LockSupport.park();
				if (Thread.interrupted()) {
					logger.info(getName() + "被中断了");
				}
				logger.info(getName() + "执行结束");
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(100);
		t2.start();
		t1.interrupt();
		LockSupport.unpark(t2);
	}
	
}
