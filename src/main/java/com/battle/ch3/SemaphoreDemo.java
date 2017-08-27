package com.battle.ch3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

public class SemaphoreDemo implements Runnable {
	private static Semaphore semap = new Semaphore(5);
	private static Logger logger = Logger.getLogger(SemaphoreDemo.class);
	
	@Override
	public void run() {
		try {
			semap.acquire();
			Thread.sleep(2000);
			logger.info(Thread.currentThread().getId() + " job finished");
		} catch (InterruptedException e) {
			logger.debug(e.getMessage());
		} finally {
			semap.release();
		}
		
	}
	
	public static void main(String[] args) {
		SemaphoreDemo r1 = new SemaphoreDemo();
		ExecutorService nftp = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 20; i++) {
			nftp.submit(r1);
		}

	}
}
