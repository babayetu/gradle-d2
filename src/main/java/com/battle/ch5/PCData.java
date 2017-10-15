package com.battle.ch5;

public class PCData {
	private final int data;

	public PCData(int data) {
		this.data = data;
	}
	
	public PCData(String data) {
		this.data = Integer.valueOf(data);
	}

	public int getData() {
		return data;
	}

	@Override
	public String toString() {
		return "PCData [data=" + data + "]";
	}

}
