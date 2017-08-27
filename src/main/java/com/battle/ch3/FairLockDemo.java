package com.battle.ch3;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 * 演示重点，低效率的ReentrantLock公平锁
 * @author jjliu
 *
 */
public class FairLockDemo implements Runnable {
	private static ReentrantLock fairLock = new ReentrantLock(true);
	private static Logger logger = Logger.getLogger(FairLockDemo.class);
	
	@Override
	public void run() {
		while (true) {
			try {
				fairLock.lock();
				logger.info(Thread.currentThread().getName() + " get lock");
			} finally {
				fairLock.unlock();
			}
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		BasicConfigurator.configure();
		FairLockDemo fld = new FairLockDemo();
		Thread t1 = new Thread(fld);
		Thread t2 = new Thread(fld);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
