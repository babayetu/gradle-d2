package com.battle.ch5;

public class PCData {
	private int data;

	public PCData() {
		
	}
	
	public PCData(int data) {
		this.data = data;
	}
	
	public PCData(String data) {
		this.data = Integer.valueOf(data);
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "PCData [data=" + data + "]";
	}

}
