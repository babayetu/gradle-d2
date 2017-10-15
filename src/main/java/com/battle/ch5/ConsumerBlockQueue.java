package com.battle.ch5;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ConsumerBlockQueue implements Runnable{
	private volatile boolean isRunning = true;
	private BlockingQueue<PCData> queue;
	private String name;
	
	public ConsumerBlockQueue(String name, BlockingQueue<PCData> queue) {
		this.queue = queue;
		this.name = name;
	}

	public void stop() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			Random r = new Random();
			
			try {
				PCData oneData = queue.take();
				if (oneData != null) {
					System.out.println(name + " consumed " + oneData);
				}
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}
