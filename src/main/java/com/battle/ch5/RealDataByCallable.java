package com.battle.ch5;

import java.util.concurrent.Callable;

public class RealDataByCallable implements Callable<String>{
	private String para;
		
	public RealDataByCallable(String para) {
		this.para = para;
	}


	@Override
	public String call() throws Exception {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < 10; i++) {
			sb.append(para);
			Thread.sleep(100);
		}
		
		return sb.toString();
	}


}
