package com.battle.ch3;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class CopyOnWriteDemo<E> {
	private static Logger logger = Logger.getLogger(CopyOnWriteDemo.class);
	private volatile transient Object[] mArray;
	private volatile int last;
	public E[] inputValue;

	public CopyOnWriteDemo() {
		mArray = new Object[5];
		last = -1;
	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		if (last <= 0 || index >= mArray.length) {
			return null;
		} else {
			return (E) mArray[index];
		}
	}

	public boolean add(E e) {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try {
			if (last + 1 < mArray.length) {
				mArray[last + 1] = e;
			} else {
				logger.info("需要扩容,last=" + last + " 容量：" + mArray.length);
				int oldLength = mArray.length;
				Object[] newArray = Arrays.copyOf(mArray, oldLength + 1);
				newArray[oldLength] = e;
				mArray = newArray;
				
			}
			last = last + 1;
			return true;
		} finally {
			lock.unlock();
		}
	}

	public class readThread implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < mArray.length; i++) {
				logger.info("Thread:" + Thread.currentThread().getId() + " read[" + i + "]=" + get(i));
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public class writeThread implements Runnable {
		@Override
		public void run() {
			try {
				for (int j = 0; j < inputValue.length; j++) {
					add(inputValue[j]);
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		CopyOnWriteDemo<String> demo = new CopyOnWriteDemo<String>();
		demo.inputValue = new String[]{"ljj","yeye","iphone","BMW","Benz","SpaceX","Comlunbus"};
		
		Thread t2 = new Thread(demo.new writeThread());	
		Thread t1 = new Thread(demo.new readThread());
		t1.start();	
		t2.start();
		t2.join();
		t1.join();
		Thread t3 = new Thread(demo.new readThread());
		t3.start();
		t3.join();
	}
}
