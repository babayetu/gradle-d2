package com.battle.ch3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SchedueldExecutorServiceDemo {
	private static Logger logger = Logger.getLogger(SchedueldExecutorServiceDemo.class);
	
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		ses.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info(System.currentTimeMillis() / 1000);
			}
			
		}, 0, 2, TimeUnit.SECONDS);
		
		
	}
}
