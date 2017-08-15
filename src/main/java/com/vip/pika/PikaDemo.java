package com.vip.pika;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;  

public class PikaDemo {

	public static void main(String[] args) {
		Jedis  jr = new Jedis ("10.198.195.243",9221);
		PikaDemo pikaDemo = new PikaDemo();
		//pikaDemo.basicTest(jr);
		pikaDemo.testHashMap(jr);
	}
	
	public void basicTest(Jedis  redis) {
		redis.set("name", "wangjun1");
		String name = redis.get("name");
		System.out.println("pika get name as:" + name);
	}
	
	public void testHashMap(Jedis  redis) {
		Map<String, String> hash = new HashMap<String,String>();
		hash.put("total", "100");
		hash.put("leavings", "50");
		redis.hmset("ljj_msize_1", hash );
		hash.put("total", "200");
		hash.put("leavings", "100");
		redis.hmset("ljj_msize_2", hash );
		
		List<String> result1 = redis.hmget("ljj_msize_1","total","leavings");
		List<String> result2 = redis.hmget("ljj_msize_2","total","leavings");
		
		System.out.println(result1);
		System.out.println(result2);
	}
}

