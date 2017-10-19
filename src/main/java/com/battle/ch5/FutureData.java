package com.battle.ch5;

public class FutureData implements AbstractData {
	protected RealData realData = null;
	protected volatile boolean isReady = false;
	
	public synchronized void setRealData(RealData realData) {
		this.realData = realData;
		isReady = true;
		notify();
	}
	
	@Override
	public synchronized String getResult() {
		while(!isReady) {
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return realData.result;
	}

}
