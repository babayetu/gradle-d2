package com.battle.ch3;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ExtThreadPool {
	private static Logger logger = Logger.getLogger(ExtThreadPool.class);
	
	public static class AnTask implements Runnable {
		public String name;
		
		
		public AnTask(String name) {
			this.name = name;
		}

		
		

		@Override
		public String toString() {
			return "AnTask [name=" + name + "]";
		}

		@Override
		public void run() {
			logger.info("正在执行 " + Thread.currentThread().getId() + ", task name:" + name);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(5,5,0,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>()) {
			@Override
			protected void beforeExecute(Thread paramThread, Runnable r) {
				logger.info("准备执行 " + r.toString());
			}
			
			@Override
			protected void afterExecute(Runnable r, Throwable paramThrowable) {
				logger.info("执行完毕 " +  r.toString());
			}
			
			@Override
			protected void terminated() {
				logger.info("线程池退出");
			}
			
		};
		
		for (int i = 0; i < 10; i++) {
			AnTask anTask = new AnTask("AnTask-" + i);
			tpe.submit(anTask);
		}
		
		tpe.shutdown();
	}
}
