package com.battle.ch2;

/**
 * 演示重点，synchronized 加锁在对象
 * synchronized加锁在普通方法：相当于加锁在当前线程对象
 * synchornized加锁在静态方法：相当于加锁在
 * @author jingjing03.liu
 *
 */
public class ThreeSynchronizedType implements Runnable {
	static ThreeSynchronizedType instance = new ThreeSynchronizedType();
	static int count = 0;
	
	/**
	 * 不同的ThreeSynchronizedType实例
	 * public synchronized void increase() 锁不住的
	 * 需要所在类对象上，相当于ThreeSynchronizedType.class对象上
	 */
	public static synchronized void increase() {
		count++;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 100000; i++) {
			increase();
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new ThreeSynchronizedType());
		Thread t2 = new Thread(new ThreeSynchronizedType());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(count);
	}
}
