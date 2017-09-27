package com.battle.ch3;

import java.util.concurrent.atomic.AtomicStampedReference;

import org.apache.log4j.Logger;
/**
 * 带有时间戳的AtomicReference，在修改数据时，同时比较时间戳
 * 实际上增加了对象状态变化的标志，而不仅仅把对象值作为唯一的expected标志
 * 最大区别是类似于乐观锁，每次compareAndSet时，更新时间戳，作为版本号，实际上未必是时间，就是版本号
 * int timeStamp = money.getStamp();
 * Long m = money.getReference();
 * money.compareAndSet(m, m-10,timeStamp,timeStamp+1)
 * @author jingjing03.liu
 *
 */
public class AtomicStampedReferenceDemo {
	private static Logger logger = Logger.getLogger(AtomicStampedReferenceDemo.class);
	
	//AtomicReference是对象的原子引用，可使用上面的原子操作
	private static AtomicStampedReference<Long> money = new AtomicStampedReference<Long>(19L,0);
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread[] ta = new Thread[4];
		
		//充值检查线程
		for (int i = 0; i < 3; i++) {
			int timeStamp = money.getStamp();
			ta[i]=new Thread() {
				public void run() {
					while(true) {
						Long m = money.getReference();
						if (m<20L) {
							if (money.compareAndSet(m, m+20,timeStamp,timeStamp+1)) {
								logger.info("余额小于20，充值成功，余额:" + money.getReference());
							}
						}
					}
				}
			};
		}
		
		//消费线程
		ta[3] = new Thread() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					while(true) {
						int timeStamp = money.getStamp();
						Long m = money.getReference();
						if (m>10) {
							logger.info("大于10元");
							if (money.compareAndSet(m, m-10,timeStamp,timeStamp+1)) {
								logger.info("消费10元，余额：" + money.getReference());
								break;
							} else {
								logger.info("没有足够金额");
								break;
							}
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		for (int i = 0; i < 4; i++) {
			ta[i].start();
			
		}
		
		for (int i = 0; i < 4; i++) {
			ta[i].join();
			
		}
		
	}
}
