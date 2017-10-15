package com.battle.ch5;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ProcuderBlockQueue implements Runnable {
	private volatile boolean isRunning = true;
	private BlockingQueue<PCData> queue;
	private String name;
	
	public ProcuderBlockQueue(String name, BlockingQueue<PCData> queue) {
		this.queue = queue;
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

	public void stop() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			Random r = new Random();
			PCData data = new PCData(r.nextInt());
			queue.offer(data);
			System.out.println(name + " produced " + data);
			try {
				Thread.sleep(r.nextInt(5) * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
