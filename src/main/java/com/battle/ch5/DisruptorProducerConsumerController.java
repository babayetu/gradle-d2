package com.battle.ch5;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorProducerConsumerController {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService cachedPool = Executors.newCachedThreadPool();
		PCDataFactoy factory = new PCDataFactoy();
		
		int bufferSize = 1024;
		
		Disruptor<PCData> disruptor = new Disruptor<PCData>(factory,
				bufferSize,
				cachedPool,
				ProducerType.MULTI,
				new BlockingWaitStrategy());
		
		disruptor.handleEventsWithWorkerPool(new DisruptorConsumer(),
				new DisruptorConsumer(),
				new DisruptorConsumer(),
				new DisruptorConsumer());
		disruptor.start();
		
		RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
		DisruptorProducer producer = new DisruptorProducer(ringBuffer);
		ByteBuffer bb = ByteBuffer.allocate(8);
		for (int i = 0; true ; i++) {
			bb.putInt(0,i);
			producer.pushData(bb);
			Thread.sleep(1000);
			System.out.println("add data " + i);
		}
	}

}
