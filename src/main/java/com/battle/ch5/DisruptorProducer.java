package com.battle.ch5;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class DisruptorProducer {
	private final RingBuffer<PCData> rb;

	public DisruptorProducer(RingBuffer<PCData> rb) {
		this.rb = rb;
	}
	
	public void pushData(ByteBuffer bb) {
		//获取下一个可用的序列号sequence
		long seq = rb.next();
		
		try {
			//获取此序列号对应可用的event对象
			//我理解在Disruptor初始化的时候，就已经设置好RingBuffer内存大小了
			PCData event = rb.get(seq);
			event.setData(bb.getInt(0));
			
		} finally {
			//注明这个event可以读取了
			rb.publish(seq);
		}
	}
}
