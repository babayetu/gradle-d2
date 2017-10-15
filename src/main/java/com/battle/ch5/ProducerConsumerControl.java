package com.battle.ch5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerControl {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>();
		ProcuderBlockQueue p1 = new ProcuderBlockQueue("p1",queue);
		ProcuderBlockQueue p2 = new ProcuderBlockQueue("p2",queue);
		ProcuderBlockQueue p3 = new ProcuderBlockQueue("p3",queue);
		
		ConsumerBlockQueue c1 = new ConsumerBlockQueue("c1",queue);
		ConsumerBlockQueue c2 = new ConsumerBlockQueue("c2",queue);
		ConsumerBlockQueue c3 = new ConsumerBlockQueue("c3",queue);
		
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.submit(p1);
		pool.submit(p2);
		pool.submit(p3);
		pool.submit(c1);
		pool.submit(c2);
		pool.submit(c3);
		Thread.sleep(10000);
		pool.shutdown();
		p1.stop();
		p2.stop();
		p3.stop();
		Thread.sleep(5000);
		c1.stop();
		c2.stop();
		c3.stop();
	}

}
