package com.battle.ch3;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SelfDefinedThreadPoolDemo {

	private static Logger logger = Logger.getLogger(SelfDefinedThreadPoolDemo.class);
	
	public static class AnotherMyTask implements Runnable {

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

	public static void main(String[] args) throws InterruptedException {
		AnotherMyTask myTask = new AnotherMyTask();
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10),
				new ThreadFactory() {

					@Override
					public Thread newThread(Runnable paramRunnable) {
						Thread t = new Thread(paramRunnable);
						t.setDaemon(true);
						logger.info("create " + t);
						return t;
					}
			
		});
		
		for (int i = 0; i < 5; i++) {
			tpe.submit(myTask);
		}
		
		Thread.sleep(2000);
	}

}
