package com.battle.ch3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * 展示SimpleDateFormat的parse()函数不是线程安全的
 * 可以用ThreadLocal来为每个线程增加一个SimpleDateFormat对象
 * @author jingjing03.liu
 *
 */
public class GoodThreadLocalDemo {
	private static Logger logger = Logger.getLogger(GoodThreadLocalDemo.class);
	private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>();
	
	public static class ParseDate implements Runnable {
		int i= 0 ;
		public ParseDate(int i) {
			this.i = i;
		}
		
		@Override
		public void run() {
			try {
				if (tl.get() == null) {
					tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				}
				Date t = tl.get().parse("2017-9-27 14:25:" + i % 60);
				logger.info(i+ ": " + t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			es.execute(new ParseDate(i));
		}
		es.shutdown();
	}
}
