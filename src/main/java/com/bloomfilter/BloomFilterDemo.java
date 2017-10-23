package com.bloomfilter;

import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 演示guava库的bloom filter的用法
 * String可以这样做 BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions,fpp)
 * @author jingjing03.liu
 *
 */
public class BloomFilterDemo {
	private static Logger logger = Logger.getLogger(BloomFilterDemo.class);
	
	public static void main(String[] args) {
		BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 5, 0.01);
		filter.put(1);
		filter.put(2);
		filter.put(3);
		
		IntStream.range(0, 10000).forEach(filter::put);
		
		logger.info(filter.mightContain(1));
		logger.info(filter.mightContain(2));
		logger.info(filter.mightContain(3));
		logger.info(filter.mightContain(100));
	}

}
