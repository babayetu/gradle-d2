package com.java.forkjoin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

public class CollectionsDemo {
	private static Logger logger = Logger.getLogger(CollectionsDemo.class);

	public static void main(String[] args) {
//		@SuppressWarnings("unchecked")
//		Map synchronizedMap = Collections.synchronizedMap(new HashMap());
//		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String,String>();
		Queue<Product> q = new ConcurrentLinkedQueue<Product>();
		
		Product p1 = new Product("iphone8",111);
		Product p2 = new Product("lefeng",222);
		q.offer(p1);
		q.offer(p2);
		
		Product get1 = q.poll();
		Product get2 = q.poll();
		
		logger.info(get1);
		logger.info(get2);
	}

}
