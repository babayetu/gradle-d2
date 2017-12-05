package com.battle.ch7;

import akka.actor.AbstractActor;

public class MyWork extends AbstractActor {
	public static enum Msg {
		WORKING, DONE, CLOSE;
	}
	
	@Override
	public void preStart() {
		System.out.println("Actor创建后，初始化时调用");
	}
	
	@Override
	public void postStop() {
		System.out.println("Actor停止时调用");
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
		        .match(Msg.class, msgType -> {
		          if (msgType == Msg.WORKING) {
		        	  System.out.println("工作状态");
		          } else if(msgType == Msg.DONE) {
		        	  System.out.println("完成状态");
		          } else if(msgType == Msg.CLOSE) {
		        	  System.out.println("结束状态");
		        	  //告诉发消息的Actor，结束状态
		        	  getSender().tell(Msg.CLOSE,getSelf());
		        	  getContext().stop(getSelf());
		          }
		        })
		        .build();
	}

}
