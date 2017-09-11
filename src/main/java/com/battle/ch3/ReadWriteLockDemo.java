package com.battle.ch3;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.log4j.Logger;

public class ReadWriteLockDemo {
	private static Logger logger = Logger.getLogger(ReadWriteLockDemo.class);
	private static ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	private static ReadLock readLock = lock1.readLock();
	private static WriteLock writeLock = lock1.writeLock();
	private int value;
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(5000);
			return value;
		} finally {
			lock.unlock();
		}
	}
	
	public void handleWrite(Lock lock, int newValue) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);
			value = newValue;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();
		
		Runnable readThread = new Runnable() {

			@Override
			public void run() {
				try {
//					demo.handleRead(readLock);
					demo.handleRead(lock2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		};

		Runnable writeThread = new Runnable() {

			@Override
			public void run() {
				try {
//					demo.handleWrite(writeLock, new Random().nextInt());
					demo.handleWrite(lock2, new Random().nextInt());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		};
		
		long start = System.currentTimeMillis();
		logger.info("Start at:" + start);
		
		Thread[] ta = new Thread[20];
		for (int i = 0; i < 18; i++) {
			ta[i] = new Thread(readThread);
			ta[i].start();
		}
		
		for (int i = 18; i < 20; i++) {
			ta[i] = new Thread(writeThread);
			ta[i].start();
		}
		
		for (int i = 0; i < ta.length; i++) {
			ta[i].join();
		}
		
		logger.info("cost:" + (System.currentTimeMillis() - start) + " ms");
	}

}
