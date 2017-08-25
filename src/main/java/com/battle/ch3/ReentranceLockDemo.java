package com.battle.ch3;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * 演示重点：可重入锁，在一个线程内可以lock多次
 * 注意lock多少次，就要unlock多少次
 * @author jingjing03.liu
 *
 */
public class ReentranceLockDemo implements Runnable{
	private static Logger logger = Logger.getLogger(ReentranceLockDemo.class);
	public static ReentrantLock lock = new ReentrantLock();
	public static int count = 0;


	@Override
	public void run() {
		for (int i = 0; i < 1000000; i++) {
			lock.lock();
			lock.lock();
			try {
				count++;
			} finally {
				lock.unlock();
				lock.unlock();
			}
		}

	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new ReentranceLockDemo());
		Thread t2 = new Thread(new ReentranceLockDemo());
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		logger.info("counter is : " + count);
	}
}
