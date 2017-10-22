package com.battle.ch5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureMainByCallable {
	public static void main(String[] args) {
		FutureTask<String> task = new FutureTask<String>(new RealDataByCallable("abc"));
		ExecutorService pool = Executors.newFixedThreadPool(1);
		
		pool.submit(task);
		
		System.out.println("请求完毕");
		
		//模拟处理其他业务操作
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("得到数据:" + task.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pool.shutdown();
	}
}
