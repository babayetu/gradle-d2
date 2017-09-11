package com.battle.ch3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

/**
 * 演示重点：CyclicBarrier在每次await()调用时，会等待其他线程准备好了，一起执行
 * 			指定的Runnable()线程
 * 			这里面有2次await()，也就意味着CyclicBarrier会做2次线程准备
 * @author jingjing03.liu
 *
 */
public class CyclicBarrierDemo {
	private static Logger logger = Logger.getLogger(CyclicBarrierDemo.class);
	
	public static class Soldier implements Runnable {
		private CyclicBarrier cb;
		private String name;
		
		
		public Soldier(CyclicBarrier cb, String name) {
			this.cb = cb;
			this.name = name;
		}


		@Override
		public void run() {
			try {
				cb.await();		//等待其他士兵集合
				doWork();
				cb.await();  	//等待其他士兵任务完成
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}  
		}


		private void doWork() {
			try {
				Thread.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.info("任务完成");
		}

	}
	
	public static class BarrierRun implements Runnable {
		boolean flag = true;
		int N;
		
		
		public BarrierRun(boolean flag, int n) {
			this.flag = flag;
			N = n;
		}


		@Override
		public void run() {
			if (flag) {
				logger.info("士兵" + N +" 个，任务完成");
			} else {
				logger.info("士兵" + N +" 个，集合完毕");
				flag=true;
			}
		}		
	}
	
	public static void main(String[] args) {
		final int N = 10;
		Thread[] allSoldier = new Thread[N];
		boolean flag = false;
		
		CyclicBarrier cb = new CyclicBarrier(N, new BarrierRun(flag,N));
		for (int i = 0; i < N; i++) {
			logger.info("士兵:" + i + "报道");
			allSoldier[i] = new Thread(new Soldier(cb,String.valueOf(i)));
			allSoldier[i].start();
			//测试BrokenBarrierException
			if (i==5) {
				allSoldier[i].interrupt();
			}
		}
		
	}
}
