package com.battle.ch5;

public class RealData implements AbstractData {
	protected final String result;
	
	public RealData(String para) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			sb.append(para);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		
		result = sb.toString();
		System.out.println("RealData组装完毕");
	}


	@Override
	public String getResult() {
		return result;
	}

}
