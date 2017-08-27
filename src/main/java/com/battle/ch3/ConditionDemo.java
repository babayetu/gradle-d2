package com.battle.ch3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * 演示对于ReentrantLock来说，Condition相当于
 * synchornized的wait()和notify()
 * @author jjliu
 *
 */
public class ConditionDemo implements Runnable {
	private static ReentrantLock lock1 = new ReentrantLock();
	private static Condition nc = lock1.newCondition();
	private static Logger logger = Logger.getLogger(ConditionDemo.class);

	@Override
	public void run() {
		try {
			lock1.lock();
			try {
				nc.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
			}
			logger.info("recalled and finished my job");
		} finally {
			lock1.unlock();
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
//		BasicConfigurator.configure();
		Thread t1 = new Thread(new ConditionDemo());
		t1.start();
		Thread.sleep(2000);
		lock1.lock();
		logger.info("main thread got lock, sub-thread needs to wait lock release");
		nc.signal();
		lock1.unlock();
	}
	
}
