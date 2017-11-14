package com.battle.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureException {
	public static Integer calc(Integer para) {
		return para / 0;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<Void> cf = CompletableFuture.supplyAsync(()->calc(50))
						 .exceptionally((ex) -> {
							 System.out.println(ex);
							 return 0;
						 })
						 .thenApply((i)->Integer.toString(i))
						 .thenApply((str) -> "\""+str+"\"")
						 .thenAccept(System.out::println);
		cf.get();
	}

}
