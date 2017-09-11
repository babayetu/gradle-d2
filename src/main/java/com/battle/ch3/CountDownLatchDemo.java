package com.battle.ch3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class CountDownLatchDemo implements Runnable {
	private static Logger logger = Logger.getLogger(CountDownLatchDemo.class);
	private static CountDownLatch cdl = new CountDownLatch(10);
	private static CountDownLatchDemo demo = new CountDownLatchDemo();

	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10)*1000);
			logger.info("check complete");
			cdl.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			exec.submit(demo);
		}
		
		cdl.await();
		logger.info("fire!");
		exec.shutdown();
	}
}
