package com.battle.ch3;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

public class ThreadLocalPerformanceDemo {
	private static Logger logger = Logger.getLogger(ThreadLocalPerformanceDemo.class);
	private static final int GEN_COUNT = 10000;
	private static final int THREAD_COUNT = 4;
	private static Random rnd = new Random(123);
	
	public static ThreadLocal<Random> tlrnd = new ThreadLocal<Random>() {
		protected Random initialValue() {
	        return new Random(123);
	    }
	};
	
	public static class RndTask implements Callable<Long> {
		private int mode = 0;
		
		
		public RndTask(int mode) {
			this.mode = mode;
		}
		
		private Random getRandom() {
			if (mode == 0) {
				return rnd;
			} else if (mode == 1) {
				return tlrnd.get();
			} else {
				return null;
			}
		}

		@Override
		public Long call() throws Exception {
			long start = System.currentTimeMillis();
			for (int i = 0; i < GEN_COUNT; i++) {
				getRandom().nextInt();
			}
			long end = System.currentTimeMillis();
			logger.info(Thread.currentThread().getName() + " spend " + (end - start) + " ms");
			return end-start;
		}
		
		@SuppressWarnings("unchecked")
		public static void main(String[] args) throws InterruptedException, ExecutionException {
			ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
			
			Future<Long>[] futs =  new Future[THREAD_COUNT];
			for (int i = 0; i < THREAD_COUNT; i++) {
				futs[i] = es.submit(new RndTask(0));
			}
			long spendTime = 0;
			for (int i = 0; i < THREAD_COUNT; i++) {
				spendTime += futs[i].get();
			}
			
			logger.info("同一个实例花费时间："+spendTime+" ms");
			
			for (int i = 0; i < THREAD_COUNT; i++) {
				futs[i] = es.submit(new RndTask(1));
			}
			spendTime = 0;
			for (int i = 0; i < THREAD_COUNT; i++) {
				spendTime += futs[i].get();
			}
			logger.info("使用ThreadLocal花费时间："+spendTime+" ms");
			es.shutdown();
		}
		
	}
}
