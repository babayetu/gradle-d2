package com.battle.ch3;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * 演示重点：制造死锁，演示ReentrantLock是可以中断的
 * 打破死锁
 * @author jingjing03.liu
 *
 */
public class RerentrantLockInterrupt implements Runnable{
	private static Logger logger = Logger.getLogger(RerentrantLockInterrupt.class);
	private static ReentrantLock lock1 = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	
	private int lockValue;

	public RerentrantLockInterrupt(int lockValue) {
		this.lockValue = lockValue;
	}

	@Override
	public void run() {
		try {
			//制造死锁
			if (lockValue == 1) {
				lock1.lockInterruptibly();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				
				lock2.lockInterruptibly();
				
			} else {
				//线程2运行代码
				lock2.lockInterruptibly();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				lock1.lockInterruptibly();
			}
		} catch (InterruptedException e) {
			logger.info(Thread.currentThread().getName() + " thread interrupt detected");
		} finally {
			if (lock1.isHeldByCurrentThread()) {
				lock1.unlock();
			}
			
			if (lock2.isHeldByCurrentThread()) {
				lock2.unlock();
			}
			
			logger.info(Thread.currentThread().getName() + " thread exit");
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new RerentrantLockInterrupt(1));
		Thread t2 = new Thread(new RerentrantLockInterrupt(2));
		t1.start();
		t2.start();
		Thread.sleep(2000);
		t2.interrupt();
	}
	
}
