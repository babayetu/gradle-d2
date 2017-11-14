package com.battle.ch6;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock的陷阱，readLock无法获得锁，因为被writeLock锁定了
 * 这时候发生中断，park()遇到中断直接返回，不会抛出异常
 * StampedLock的死循环逻辑中(自旋锁)，没有处理异常，在循环中出不来，疯狂占用CPU
 * 
 * @author jingjing03.liu
 *
 */
public class StampedLockCPUDemo {
	static Thread[] holdCPUThreads = new Thread[3];
	static final StampedLock lock = new StampedLock();
	
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			public void run() {
				long readLong = lock.writeLock();
				LockSupport.parkNanos(600000000000L);
				lock.unlockWrite(readLong);
			}
		}.start();
		
		Thread.sleep(100);
		
		for (int i = 0; i < 3; i++) {
			holdCPUThreads[i] = new Thread(new HoldCPUReadThread());
			holdCPUThreads[i].start();
		}
		
		Thread.sleep(10000);
		
		for (int i = 0; i < 3; i++) {
			holdCPUThreads[i].interrupt();;
		}
	}
	
	private static class HoldCPUReadThread implements Runnable {

		@Override
		public void run() {
			long lockRead = lock.readLock();
			System.out.println(Thread.currentThread().getName() + " 获得读锁");
			lock.unlockRead(lockRead);
			
		}
		
	}
}
