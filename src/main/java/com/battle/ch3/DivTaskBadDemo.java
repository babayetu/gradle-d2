package com.battle.ch3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 演示重点：线程池会吃掉线程抛出的异常，比如除零异常
 * 有益处的做法：向线程池拿回异常堆栈
 * @author jingjing03.liu
 *
 */
public class DivTaskBadDemo implements Runnable {
	private static Logger logger = Logger.getLogger(DivTaskBadDemo.class);
	int a,b;
	
	
	
	public DivTaskBadDemo(int a, int b) {
		this.a = a;
		this.b = b;
	}



	@Override
	public void run() {
		double re = a/b;
		logger.info(re);	
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
							0,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
		
		for (int i = 0; i < 5; i++) {
			//一个简单的修改方式，拿到future自行获取线程异常堆栈
			Future<?> re = pool.submit(new DivTaskBadDemo(100,i));
			try {
				re.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
