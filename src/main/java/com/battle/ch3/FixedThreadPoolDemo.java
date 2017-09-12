package com.battle.ch3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class FixedThreadPoolDemo {
	private static Logger logger = Logger.getLogger(FixedThreadPoolDemo.class);
	
	public static class MyTask implements Runnable {

		@Override
		public void run() {
			logger.info(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId() + " 正在执行");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
//		ExecutorService es = Executors.newFixedThreadPool(5);
		ExecutorService es = Executors.newCachedThreadPool();
		
		MyTask myTask = new MyTask();
		
		for (int i = 0; i < 10; i++) {
			es.submit(myTask);
		}
		
		try {
			es.awaitTermination(10, TimeUnit.SECONDS);
			es.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
