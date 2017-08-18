package com.battle.ch2;

import org.apache.log4j.Logger;
/**
 * 演示重点，jvm只有守护进程时，程序自动退出
 * setDaemon()之后，new DaemonThread不会一直执行了
 * @author jingjing03.liu
 *
 */
public class DaemonDemo {
	private static Logger logger = Logger.getLogger(DaemonDemo.class);
	
	public static class DaemonThread extends Thread {
		@Override
		public void run() {
			logger.info("i am alive");
			
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread dt = new DaemonThread();
		dt.setDaemon(true);
		dt.start();
		
		Thread.sleep(2000);
	}

}
