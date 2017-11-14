package com.battle.ch6;

import java.util.concurrent.CompletableFuture;

/**
 * thenCombine的语义是，执行第一个CompletableFuture，然后执行第一个参数
 * 的CompletableFuture，把两个结果作为入参给BiFunction函数
 * @author jingjing03.liu
 *
 */
public class CompletableBiFuncDemo {
	public static int calc(Integer para) {
		return para / 2;
	}
	
	public static void main(String[] args) {
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->calc(50));
		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(()->calc(25));
		
		future1.thenCombine(future2, (i,j)->(i+j)).thenAccept(System.out::println);
	}

}
