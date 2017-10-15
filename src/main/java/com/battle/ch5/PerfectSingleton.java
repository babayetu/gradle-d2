package com.battle.ch5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.battle.ch4.AtomicIntegerFieldUpdaterDemo;

public class PerfectSingleton {
	private static Logger logger = Logger.getLogger(PerfectSingleton.class);
	private PerfectSingleton() {
		logger.info("PerfectSingletonHolder is created");
	}
	
	private static class PerfectSingletonHolder {
		private static PerfectSingleton instance = new PerfectSingleton();
	}
	
	public static PerfectSingleton getInstance() {
		return PerfectSingletonHolder.instance;
	}
	
	private static class ManyRunnable implements Runnable{

		@Override
		public void run() {
			PerfectSingleton.getInstance();
		}
		
	}
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			pool.submit(new ManyRunnable());
		}
		
		pool.shutdown();
		
		//不间断检查线程池是否退出
        while (true) {
            if (pool.isTerminated()) {
                logger.info("线程池任务执行完毕");
                break;
            }
        }
	}
}
