package com.battle.ch5;

public class Client {
	public FutureData request(final String queryStr) {
		FutureData futureData = new FutureData();
		new Thread() {
			public void run() {
				RealData realData = new RealData(queryStr);
				futureData.setRealData(realData);
			}
		}.start();
		return futureData;
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		FutureData resp = client.request("abc");
		System.out.println("调用完毕返回");
		
		System.out.println("异步获取数据为:" + resp.getResult());
	}
}
