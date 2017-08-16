package com.battle.ch2;

import org.apache.log4j.Logger;

/**
 * 演示重点，ChangeThread线程内suspend，用wait/notify来保持同步
 * 避免resume在suspend之前发生
 * @author jingjing03.liu
 *
 */
public class GoodSuspend {
	private static Logger logger = Logger.getLogger(GoodSuspend.class);
	
	private static Object o = new Object();
	
	private static class ChangeObjectThread extends Thread {
		volatile boolean suspendMe = false;	
		
		public void SuspendMe() {
			suspendMe = true;
		}
		
		/**
		 * 外部开关控制notify，让挂起结束
		 *
		 */
		public void ResumeMe() {
			suspendMe = false;
			synchronized(this) {
				notify();
			}
		}

		@Override
		public void run() {
			while(true) {
				synchronized(this) {
					/**
					 * 检查自己是否被挂起
					 * 用wait等待
					 */
					while(suspendMe) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
				
				/**
				 * 正常业务功能
				 */
				synchronized(o) {
					logger.info("in ChangeObjectThread");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Thread.yield();
			}

		}
	}

	private static class ReadObjectThread extends Thread {

		@Override
		public void run() {
			while(true) {
				synchronized(o) {
					logger.info("in ReadObjectThread");	
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Thread.yield();
			}
			
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ChangeObjectThread cot = new ChangeObjectThread();
		ReadObjectThread rot = new ReadObjectThread();
		
		cot.start();
		rot.start();
		Thread.sleep(100);
		cot.SuspendMe();
		logger.info("change thread suspend 5s");
		Thread.sleep(5000);
		logger.info("change thread resume");
		cot.ResumeMe();
		
	}
	
	
}
