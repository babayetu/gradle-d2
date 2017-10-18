package com.battle.ch5;

/**
 * 演示Disruptor使用数据前后padding
 * 来使得每一行cache line只包含一个sequence的数据，减小CPU cache重新获取的时间花费
 * @author jingjing03.liu
 *
 */
public class DisruptorPadding implements Runnable{
	public final static int NUM_THREADS = 2;
	public final static long ITERATIONS = 500L * 1000L * 1000L;
	private final int arrayIndex;
	
	private static class VolatileLong {
		//每次value更新，都会触发CPU某个核cache刷新
		public volatile long value = 0L;
		//加上padding，会使得value在一行中   花费4961ms
		//不加padding，防止value和第二个value进入同一行，增加刷新cpu cache的次数
		//public long p1,p2,p3,p4,p5,p6,p7;    花费18270ms
	}
	
	private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
	
	static {
		for (int i = 0; i < longs.length; i++) {
			longs[i] = new VolatileLong();
		}
	}
	
	public DisruptorPadding(final int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	
	private static void runTest() throws InterruptedException {
		Thread[] threads = new Thread[NUM_THREADS];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new DisruptorPadding(i));
		}
		
		for (Thread t: threads) {
			t.start();
		}
		
		for (Thread t: threads) {
			t.join();
		}		
	}
	
	@Override
	public void run() {
		long i = ITERATIONS + 1;
		while (0 != --i) {
			longs[arrayIndex].value = i;
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		final long startTime = System.currentTimeMillis();
		runTest();
		System.out.println("used time: " + (System.currentTimeMillis() - startTime) + " ms");
	}
	
}
