package com.battle.ch7;

import akka.actor.AbstractActor;

public class Greeter extends AbstractActor {
	public static enum Msg {
		GREET,DONE;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
		        .match(Msg.class, msgType -> {
		          if (msgType == Msg.GREET) {
		        	  System.out.println("hello akka greet world");
		        	  getSender().tell(Msg.DONE,getSelf());
		          }
		        })
		        .build();
	}


}
