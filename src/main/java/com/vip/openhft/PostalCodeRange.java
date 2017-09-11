package com.vip.openhft;

import java.io.Serializable;

public class PostalCodeRange implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int minCode;
	private int maxCode;
	
	public int getMinCode() {
		return minCode;
	}
	public void setMinCode(int minCode) {
		this.minCode = minCode;
	}
	public int getMaxCode() {
		return maxCode;
	}
	public void setMaxCode(int maxCode) {
		this.maxCode = maxCode;
	}
	public PostalCodeRange(int minCode, int maxCode) {
		this.minCode = minCode;
		this.maxCode = maxCode;
	}
	@Override
	public String toString() {
		return "PostalCodeRange [minCode=" + minCode + ", maxCode=" + maxCode + "]";
	}
	
	public static void main(String[] args) {
		System.out.println(ObjectSizeFetcher.getObjectSize(new PostalCodeRange(1,2)));
	}
}
