package com.battle.ch2;

public class ThreadRunnable implements Runnable{
	public static void main(String[] args) {
		Thread t1 = new Thread(new ThreadRunnable());
		t1.start();
	}
	
	@Override
	public void run() {
		System.out.println("from runnable thread init");
		
	}
	
}
