package com.battle.ch3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;


public class ThreadLocalGCDemo {
	private static Logger logger = Logger.getLogger(ThreadLocalGCDemo.class);
	private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
		protected void finalize() throws Throwable{
			logger.info(this.toString() + " is gc");
		}
	};
	private static volatile CountDownLatch cd = new CountDownLatch(10000);
	
	public static class ParseDate implements Runnable {
		int i= 0 ;
		public ParseDate(int i) {
			this.i = i;
		}
		
		@Override
		public void run() {
			try {
				if (tl.get() == null) {
					tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
						/**
						 * 
						 */
						private static final long serialVersionUID = 466573375307972116L;

						protected void finalize() throws Throwable{
							logger.info(this.toString() + " is gc");
						}
					});
					logger.info(Thread.currentThread().getId() + ":create SimpleDateFormat");
				}
				Date t = tl.get().parse("2017-9-27 14:25:" + i % 60);
//				logger.info(i+ ": " + t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				cd.countDown();
			}
			
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		logger.info("mission complete");
		tl = null;
		System.gc();
		logger.info("first gc complete");
		
		//在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象
		tl = new ThreadLocal<SimpleDateFormat>();
		cd = new CountDownLatch(10000);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		Thread.sleep(1000);
		System.gc();
		logger.info("second gc complete");
		
		es.shutdown();
	}
}
