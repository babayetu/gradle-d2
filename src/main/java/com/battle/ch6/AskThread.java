package com.battle.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AskThread implements Runnable {
	private CompletableFuture<Integer> future = null;
	
	
	public AskThread(CompletableFuture<Integer> future) {
		this.future = future;
	}

	@Override
	public void run() {
		int myRet = 0;
		
		try {
			myRet = future.get() * future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("最终结果是" + myRet);
	}
	
	public static void main(String[] args) throws InterruptedException {
		CompletableFuture<Integer> cf = new CompletableFuture<Integer>();
		new Thread(new AskThread(cf)).start();
		Thread.sleep(2000);
		cf.complete(60);
	}

}
