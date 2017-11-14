package com.battle.ch6;

import java.util.concurrent.CompletableFuture;

public class CompletableThenComposeDemo {
	public static int calc(Integer para) {
		return para / 2;
	}
	
	public static void main(String[] args) {
		CompletableFuture.supplyAsync(()->calc(50))
							.thenCompose((i) -> CompletableFuture.supplyAsync(()->calc(i)))
							.thenApply((str) -> "\""+str+"\"")
							.thenAccept(System.out::println);
		
	}
}
