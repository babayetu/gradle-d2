package com.battle.ch2;

import org.apache.log4j.Logger;
/**
 * object得到通知后，还需要获得对象锁，才可以继续执行
 * wait()释放锁，notify()不释放锁，知道线程退出
 * @author jingjing03.liu
 *
 */
public class WaitNotifyReleaseLock {
	private static Logger logger = Logger.getLogger(WaitNotifyReleaseLock.class);
	private static Object object = new Object();

	private static class Thread1 extends Thread {
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":t1 started");
				try {
					System.out.println(System.currentTimeMillis() + ":t1 wait and release lock");
					object.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + ":t1 ends");
			}
		}
	}

	private static class Thread2 extends Thread {
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":t2 started");
				System.out.println(System.currentTimeMillis() + ":t2 notify and not release lock until thread exit");
				object.notify();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + ":t2 ends");
			}
		}
	}

	public static void main(String[] args) {
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		t1.start();
		t2.start();

	}

}
