package com.battle.ch3;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * 演示重点，在执行几分钟后，两个job总能结束
 * 因为tryLock()不会block，获取锁失败，直接返回
 * @author jingjing03.liu
 *
 */
public class TryLockDemo implements Runnable {
	private static Logger logger = Logger.getLogger(TryLockDemo.class);
	private static ReentrantLock lock1 = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	private int lockSign;

	public TryLockDemo(int lockSign) {
		this.lockSign = lockSign;
	}

	@Override
	public void run() {
		if (lockSign == 1) {
			while (true) {
				if (lock1.tryLock()) {
					try {
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (lock2.tryLock()) {
							try {
								logger.info(lockSign + " job is done");
								return;
							} finally {
								lock2.unlock();
							}

						}
					} finally {
						lock1.unlock();
					}
				}

			}
		} else {
			while (true) {
				if (lock2.tryLock()) {
					try {
						try {
							Thread.sleep(70);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (lock1.tryLock()) {
							try {
								logger.info(lockSign + " job is done");
								return;
							} finally {
								lock1.unlock();
							}
						}
					} finally {
						lock2.unlock();
					}
				}

			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new TryLockDemo(1));
		Thread t2 = new Thread(new TryLockDemo(2));
		t1.start();
		t2.start();
//		t1.join();
//		t2.join();
	}
}
