package com.battle.ch3;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.log4j.Logger;

public class ConcurrentSkipListDemo {
	private static Logger logger = Logger.getLogger(ConcurrentSkipListDemo.class);
	
	public static void main(String[] args) {
		Map<Integer, Integer> map = new ConcurrentSkipListMap<Integer,Integer>();
		for (int i = 0; i < 30; i++) {
			map.put(i, i);
		}
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			logger.info(entry.getKey());
		}
	}

}
