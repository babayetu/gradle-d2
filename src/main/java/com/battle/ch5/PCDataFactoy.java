package com.battle.ch5;

import com.lmax.disruptor.EventFactory;

public class PCDataFactoy implements EventFactory<PCData>{

	@Override
	public PCData newInstance() {
		return new PCData();
	}

}
