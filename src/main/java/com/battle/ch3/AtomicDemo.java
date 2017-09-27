package com.battle.ch3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class AtomicDemo {
	private static Logger logger = Logger.getLogger(AtomicDemo.class);

	private static AtomicInteger count = new AtomicInteger(0);

	public static class AddThread implements Runnable {

		@Override
		public void run() {
			logger.info(Thread.currentThread().getId() + " 正在执行");
			for (int i = 0; i < 1000; i++) {
				count.incrementAndGet();
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
//		 Thread[] ts = new Thread[10];
//		 for (int k = 0; k < ts.length; k++) {
//		 ts[k] = new Thread(new AddThread());
//		 }
//		
//		 for (int k = 0; k < ts.length; k++) {
//		 ts[k].start();
//		 }
//		
//		 for (int k = 0; k < ts.length; k++) {
//		 ts[k].join();
//		 }

		ExecutorService es = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
			es.submit(new AddThread());
		}
		
		//不再接受新任务，也不会终止正在执行的老任务
		es.shutdown();

		while (true) {
			if (es.isTerminated()) {
				logger.info("线程池任务执行完毕");
				break;
			}
		}
		logger.info("final count is " + count);

	}
}
