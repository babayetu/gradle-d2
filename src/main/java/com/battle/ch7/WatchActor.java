package com.battle.ch7;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WatchActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
	
	
	public WatchActor(ActorRef ref) {
		getContext().watch(ref);
	}


	@Override
	public Receive createReceive() {
		return receiveBuilder()
		        .match(Terminated.class, terminatedMsg -> {
		          System.out.println(terminatedMsg.getActor().path() + " has terminated, shutdown the system");
		          getContext().system().terminate();
		        })
		        .build();
	}

}
