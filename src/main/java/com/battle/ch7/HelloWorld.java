package com.battle.ch7;

import com.battle.ch7.Greeter.Msg;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class HelloWorld extends AbstractActor {
	ActorRef greeter;

	@Override
	public void preStart() {
		greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
		System.out.println("Greeter Actor path: " + greeter.path());
		greeter.tell(Greeter.Msg.GREET, getSelf());
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
		        .match(Msg.class, msgType -> {
		          if (msgType == Msg.DONE) {
		        	  greeter.tell(Greeter.Msg.GREET, getSelf());
		        	  getContext().stop(getSelf());
		          }
		        })
		        .build();
	}

}
