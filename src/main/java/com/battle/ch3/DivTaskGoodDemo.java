package com.battle.ch3;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class DivTaskGoodDemo implements Runnable{
	private static Logger logger = Logger.getLogger(DivTaskGoodDemo.class);
	int a,b;
	
	
	
	public DivTaskGoodDemo(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		double re = a/b;
		logger.info(re);	
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor pool = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE,
							0,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
		
		for (int i = 0; i < 5; i++) {
			pool.execute(new DivTaskGoodDemo(100,i));
		}
	}
}
