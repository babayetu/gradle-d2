package com.battle.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 演示匿名内部类()->calc(50)
 * 自动绑定在Suppier<U>接口的唯一get()函数上，也就是get()函数就是calc()函数
 * @author jingjing03.liu
 *
 */
public class CompletableFutureAsync {
	private static Integer calc(int para) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return para * para;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<Void> cf = CompletableFuture.supplyAsync(()->calc(50))
				.thenApply((i)->Integer.toString(i))
				.thenApply((str)->"\""+str+"\"")
				.thenAccept(System.out::println);
		
		//等待daemon子线程完成任务，不然主线程退出，子线程不执行完也会一起退出
		cf.get();
	}
}
