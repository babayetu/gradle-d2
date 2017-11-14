package com.battle.ch6;

import java.util.concurrent.locks.StampedLock;

public class StampedLockPoint {
	public double x, y;
	private final StampedLock sl = new StampedLock();

	private void move(double deltaX, double deltaY) {

		long stamp = sl.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlock(stamp);
		}

	}
	
	private double distanceFromOrigin() {
		//引入乐观读来增加系统的并行度
		long stamp = sl.tryOptimisticRead();
		double currX = x;
		double currY = y;
		if (!sl.validate(stamp)) {
			//stamp判断已经被别的线程修改了
			//升级读锁为悲观锁
			System.out.println("被别的线程修改了，升级为悲观锁");
			stamp = sl.readLock();
			try {
				currX = x;
				currY = y;
			} finally {
				sl.unlockRead(stamp);
			}
		}
		
		return Math.sqrt(currX * currX + currY * currY);
	}
	
	class changeObject implements Runnable {
		private StampedLockPoint sp;
		
		public changeObject(StampedLockPoint sp) {
			this.sp = sp;
		}

		@Override
		public void run() {
			int i = 0;
			while (i < 10) {
				i++;
				sp.move(1, 2);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}

	class readObject implements Runnable {
		private StampedLockPoint sp;
		
		public readObject(StampedLockPoint sp) {
			this.sp = sp;
		}

		@Override
		public void run() {
			int i = 0;
			while (i < 10) {
				i++;
				System.out.println(sp.distanceFromOrigin());
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
		StampedLockPoint slp = new StampedLockPoint();
		slp.x= 10;
		slp.y = 10;
		Thread t1 = new Thread(slp.new changeObject(slp));
		Thread t2 = new Thread(slp.new readObject(slp));
		t2.start();
		t1.start();
		t1.join();
		t2.join();

	}

}
