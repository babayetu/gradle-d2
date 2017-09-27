package com.battle.ch3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

/**
 * debug看put()和take()分别有自己的锁的情况
 * 头锁和尾锁不冲突
 * @author jingjing03.liu
 *
 */
public class LinkedBlockingQueueDemo {
	private static Logger logger = Logger.getLogger(LinkedBlockingQueueDemo.class);
	
	public static void main(String[] args) {
		BlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
		
		
		try {
			q.put(10);
			q.put(20);
			q.take();
			q.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
