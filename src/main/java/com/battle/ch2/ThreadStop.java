package com.battle.ch2;

import org.apache.log4j.Logger;

public class ThreadStop {
	private static final Logger logger = Logger.getLogger(ThreadStop.class.getName());
	
	static User u = new User();
	
	public static class User {
		private int id;
		private String name;
		public User() {
			this.id = 0;
			this.name = "0";
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
		
	}
	
	public static class ChangeThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				synchronized(u) {
					int v = (int)(System.currentTimeMillis()/1000);
					u.setId(v);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					u.setName(String.valueOf(v));
				}
				Thread.yield();
			}		
		}	
	}
	
	public static class ReadThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				synchronized(u) {
					if (u.getId() != Integer.parseInt(u.getName())) {
						logger.info(u.toString());
					}				
				}
			}
			
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new ReadThread()).start();
		
		while(true) {
			Thread t = new Thread(new ChangeThread());
			t.start();
			Thread.sleep(150);
			t.stop();
			
		}
	}
}
