package com.battle.ch6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
	private static final int MAX_THREADS = 3;
	private static final int TASK_COUNT = 3;
	private static final int TARGET_COUNT = 10000000;
	
	private AtomicLong account = new AtomicLong(0L);
	private LongAdder laCount = new LongAdder();
	private long count = 0;
	
	static CountDownLatch cdlSync = new CountDownLatch(TASK_COUNT);
	static CountDownLatch cdlAtomic = new CountDownLatch(TASK_COUNT);
	static CountDownLatch cdlLongAdder = new CountDownLatch(TASK_COUNT);
	
	//带锁操作
	protected synchronized long inc() {
		return ++count;
	}
	
	protected synchronized long getCount() {
		return count;
	}
	
	public class SyncThread implements Runnable {
		protected String name;
		protected long startTime;
		LongAdderDemo out;
		
		
		public SyncThread(long startTime, LongAdderDemo out) {
			this.startTime = startTime;
			this.out = out;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long v = out.getCount();
			while(v<TARGET_COUNT) {
				v=out.inc();
			}
			
			long endTime  = System.currentTimeMillis();
			System.out.println("Sync spend " + (endTime - startTime) + "ms. v="+v);
			cdlSync.countDown();
		}			
	}
	
	public void testSync() throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
		long startTime  = System.currentTimeMillis();
		SyncThread syncThread = new SyncThread(startTime, this);
		for (int i = 0; i < TASK_COUNT; i++) {
			es.submit(syncThread);
		}
		
		cdlSync.await();
		es.shutdown();
	}
	
	private class AtomicThread implements Runnable {
		protected String name;
		protected long startTime;
		
		
		public AtomicThread(long startTime) {
			this.startTime = startTime;
		}


		@Override
		public void run() {
			long v = account.get();
			while(v<TARGET_COUNT) {
				v= account.incrementAndGet();
			}
			long endTime  = System.currentTimeMillis();
			System.out.println("Atomic spend " + (endTime - startTime) + "ms. v="+v);
			cdlAtomic.countDown();
		}
		
	}
	
	public void testAtomic() throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
		long startTime  = System.currentTimeMillis();
		AtomicThread atomicThread = new AtomicThread(startTime);
		for (int i = 0; i < TASK_COUNT; i++) {
			es.submit(atomicThread);
		}
		
		cdlAtomic.await();
		es.shutdown();
	}

	private class LongAdderThread implements Runnable {
		protected String name;
		protected long startTime;
		
		
		public LongAdderThread(long startTime) {
			this.startTime = startTime;
		}


		@Override
		public void run() {
			long v = laCount.sum();
			while(v<TARGET_COUNT) {
				laCount.increment();
				v = laCount.sum();
			}
			long endTime  = System.currentTimeMillis();
			System.out.println("LongAdder spend " + (endTime - startTime) + "ms. v="+v);
			cdlLongAdder.countDown();
		}
		
	}
	
	public void testLongAdder() throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
		long startTime  = System.currentTimeMillis();
		LongAdderThread atomicThread = new LongAdderThread(startTime);
		for (int i = 0; i < TASK_COUNT; i++) {
			es.submit(atomicThread);
		}
		
		cdlLongAdder.await();
		es.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		LongAdderDemo lad = new LongAdderDemo();
		lad.testSync();
		lad.testAtomic();
		lad.testLongAdder();

	}

}
