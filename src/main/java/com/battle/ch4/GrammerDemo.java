package com.battle.ch4;

import org.apache.log4j.Logger;

/**
 * 语法实验
 * @author jingjing03.liu
 *
 */
public class GrammerDemo {
	private static Logger logger = Logger.getLogger(GrammerDemo.class);
	
	static class LeadingZeoDemo {
		public int leadingZero(int value) {
			return Integer.numberOfLeadingZeros(value);
		}
	}
	
	static class IndexCalculator {
		public void tryCalculate(int idx) {
			int pos = idx + 8;
			logger.info("pos is " + pos);
			int zeroNumPos = Integer.numberOfLeadingZeros(pos);
			logger.info("zeroNumPos is " + zeroNumPos);
			int zeroNumFirst = 28;
			int bucketIdx = zeroNumFirst - zeroNumPos;
			logger.info("第几个数组： " + bucketIdx);
			int index = (0x80000000 >>> zeroNumPos)^pos;
			logger.info("数组内部下标是： " + index);
		}
	}
	
	public static void main(String[] args) {
//		LeadingZeoDemo lzd = new LeadingZeoDemo();
//		logger.info(lzd.leadingZero(0));
//		logger.info(lzd.leadingZero(8));
//		logger.info(lzd.leadingZero(16));
//		logger.info(lzd.leadingZero(32));
//		logger.info(lzd.leadingZero(64));
//		logger.info(lzd.leadingZero(128));
		
		IndexCalculator ic = new IndexCalculator();
		ic.tryCalculate(10);
	}
}
