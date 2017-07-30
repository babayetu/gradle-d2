package com.sevendays.ch1;

import java.util.concurrent.locks.ReentrantLock;

public class Interruptible {
	public static void main(String[] args) throws InterruptedException {
		final ReentrantLock r1 = new ReentrantLock();
		final ReentrantLock r2 = new ReentrantLock();
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					r1.lockInterruptibly();
					Thread.sleep(1000);
					r2.lockInterruptibly();
				} catch (InterruptedException e) {
					System.out.println("t1 interrupted");
				} finally {
					// TODO: handle finally clause
				}
			}
		};

		Thread t2 = new Thread() {
			@Override
			public void run() {
				try {
					r2.lockInterruptibly();
					Thread.sleep(1000);
					r1.lockInterruptibly();
				} catch (InterruptedException e) {
					System.out.println("t2 interrupted");
				} finally {
					// TODO: handle finally clause
				}
			}
		};
		
		t1.start();
		t2.start();
		Thread.sleep(2000);
		t1.interrupt();
		t2.interrupt();
		t1.join();
		t2.join();
	}
}
