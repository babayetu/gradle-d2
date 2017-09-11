package com.vip.openhft;

import org.apache.log4j.Logger;

import net.openhft.chronicle.map.ChronicleMap;



public class OpenHFTDemo {
	private static Logger logger = Logger.getLogger(OpenHFTDemo.class);
	private static ChronicleMap<Integer, PostalCodeRange> cityPostalCodes = ChronicleMap
		    .of(Integer.class, PostalCodeRange.class)
		    .name("city-postal-codes-map")
		    .constantValueSizeBySample(new PostalCodeRange(1,2))
		    .entries(50000)
		    .create();
    

	public static void main(String[] args) throws InterruptedException {
		cityPostalCodes.put(111, new PostalCodeRange(1,2));
		cityPostalCodes.put(222, new PostalCodeRange(2,3));
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info(cityPostalCodes.get(111));
				logger.info(cityPostalCodes.get(222));
			}
			
		});
		
		t1.start();
		t1.join();
	}

}
