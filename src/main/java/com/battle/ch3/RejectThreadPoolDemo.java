package com.battle.ch3;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class RejectThreadPoolDemo {
	private static Logger logger = Logger.getLogger(RejectThreadPoolDemo.class);
	
	public static class MyTask implements Runnable {

		@Override
		public void run() {
			logger.info(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId() + " 正在执行");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		MyTask myTask = new MyTask();
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10),
				Executors.defaultThreadFactory(), new RejectedExecutionHandler() {

					@Override
					public void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor) {
						logger.info(paramRunnable.toString() + " 被拒绝执行");
						
					}
			
		});
		
		for (int i = 0; i < 1000; i++) {
			tpe.submit(myTask);
		}

	}

}
