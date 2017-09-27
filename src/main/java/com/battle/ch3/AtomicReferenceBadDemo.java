package com.battle.ch3;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

/**
 * 演示重点在AtomicReference无法避免expect值被修改2次，返回原来的值，比较后会做set动作
 * 例子是小于20元的用户只能赠送充值一次，但是实际上突破了一次的充值上限
 * @author jingjing03.liu
 *
 */
public class AtomicReferenceBadDemo {
	private static Logger logger = Logger.getLogger(AtomicReferenceBadDemo.class);
	
	//AtomicReference是对象的原子引用，可使用上面的原子操作
	private static AtomicReference<Long> money = new AtomicReference<Long>();
	
	public static void main(String[] args) throws InterruptedException {
		money.set(19L);
		
		Thread[] ta = new Thread[4];
		
		//充值检查线程
		for (int i = 0; i < 3; i++) {
			ta[i]=new Thread() {
				public void run() {
					while(true) {
						Long m = money.get();
						if (m<20L) {
							if (money.compareAndSet(m, m+20)) {
								logger.info("余额小于20，充值成功，余额:" + money.get());
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
						Long m = money.get();
						if (m>10) {
							logger.info("大于10元");
							if (money.compareAndSet(m, m-10)) {
								logger.info("消费10元，余额：" + money.get());
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
