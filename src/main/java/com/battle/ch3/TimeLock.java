package com.battle.ch3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class TimeLock implements Runnable {
	private static Logger logger = Logger.getLogger(TimeLock.class);
	private static ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {
		try {
			if (lock.tryLock(5, TimeUnit.SECONDS)) {
				logger.info(Thread.currentThread().getId() + " get lock successful");
				Thread.sleep(6000);
			} else {
				logger.info(Thread.currentThread().getId() + " get lock failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
			logger.info(Thread.currentThread().getId() + " exit");
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new TimeLock());
		Thread t2 = new Thread(new TimeLock());
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
