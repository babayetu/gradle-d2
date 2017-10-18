package com.battle.ch5;

import com.lmax.disruptor.WorkHandler;

public class DisruptorConsumer implements WorkHandler<PCData>{

	@Override
	public void onEvent(PCData event) throws Exception {
		System.out.println(Thread.currentThread().getId() + ":Event:" + event.getData() * event.getData());
	}
	
}
