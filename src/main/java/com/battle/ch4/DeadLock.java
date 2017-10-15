package com.battle.ch4;

import org.apache.log4j.Logger;

public class DeadLock extends Thread{
	private static Logger logger = Logger.getLogger(DeadLock.class);
	private Object tool;
	private static Object fork1 = new Object();
	private static Object fork2 = new Object();
	private String name;
	
	public DeadLock(Object tool) {
		this.tool = tool;
		if (tool == fork1) {
			name = "哲学家1";
		} else {
			name = "哲学家2";
		}
	}
	
	@Override
	public void run() {
		if (tool == fork1) {
			synchronized(fork1) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized(fork2) {
					logger.info(name + " 拿起了筷子，可以吃饭了");
				}
			}
		}
		
		
		if (tool == fork2) {
			synchronized(fork2) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized(fork1) {
					logger.info(name + " 拿起了筷子，可以吃饭了");
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		DeadLock ph1 = new DeadLock(fork1);
		DeadLock ph2 = new DeadLock(fork2);
		ph1.start();
		ph2.start();
		
		ph1.join();
		ph2.join();
	}
}
