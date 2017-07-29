package com.sevendays.ch1;

public class Uninterruptible {
	public static void main(String[] args) throws InterruptedException {
		final Object o1 = new Object();
		final Object o2 = new Object();
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					synchronized(o1) {
						Thread.sleep(1000);
							synchronized(o2) {}
					}
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
					synchronized(o2) {
						Thread.sleep(1000);
							synchronized(o1) {}
					}
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
		t1.join();
		t2.join();
	}
}
