package com.battle.ch4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.apache.log4j.Logger;

public class AtomicIntegerFieldUpdaterDemo {
	private static Logger logger = Logger.getLogger(AtomicIntegerFieldUpdaterDemo.class);
	
	public static class Candidate {
		volatile int score;
		int id;
	}
	
	private static AtomicInteger allScore = new AtomicInteger();
	
	public static void main(String[] args) {
		Candidate can = new Candidate();
		Thread[] t = new Thread[1000];
		
		AtomicIntegerFieldUpdater<Candidate> updater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
		for (int i = 0; i < t.length; i++) {
			t[i] = new Thread() {
				public void run() {
					if (Math.random() > 0.4) {
						allScore.getAndIncrement();
						updater.getAndIncrement(can);
					}
				}
			};
			t[i].start();
		}
		
		for (int i = 0; i < t.length; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		logger.info("allScore is :" + allScore);
		logger.info("Candidate get :" + can.score);

	}

}
