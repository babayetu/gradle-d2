package com.battle.ch3;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.apache.log4j.Logger;

public class AtomicIntegerArrayDemo {
	private static Logger logger = Logger.getLogger(AtomicIntegerArrayDemo.class);

	private static AtomicIntegerArray arr = new AtomicIntegerArray(10);
	
	public static class AddThread implements Runnable {

		@Override
		public void run() {
			for (int j = 0; j < 10000; j++) {
				arr.getAndIncrement(j%arr.length());
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] t = new Thread[10];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Thread(new AddThread());
		}
		
		for (int i = 0; i < t.length; i++) {
			t[i].start();
		}
		
		for (int i = 0; i < t.length; i++) {
			t[i].join();
		}
		
		System.out.println(arr);
	}
	
}
