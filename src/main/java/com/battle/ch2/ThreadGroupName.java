package com.battle.ch2;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ThreadGroupName implements Runnable{
	private static Logger logger = Logger.getLogger(ThreadGroupName.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		
		ThreadGroup threadGroup = new ThreadGroup("MyFirstGroup");
		Thread t1 = new Thread(threadGroup, new ThreadGroupName(), "t1" );
		Thread t2 = new Thread(threadGroup, new ThreadGroupName(), "t2" );
		t1.start();
		t2.start();
		logger.info(threadGroup.activeCount());
		threadGroup.list();
	}

	@Override
	public void run() {
		String groupAndName = Thread.currentThread().getThreadGroup().getName() +
				"-" + Thread.currentThread().getName();
		while(true) {
			logger.info("i am " + groupAndName);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
