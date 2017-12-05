package com.battle.ch7;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class Supervisor extends AbstractActor {
	private static SupervisorStrategy strategy = new OneForOneStrategy(3, 
			Duration.create(1, TimeUnit.MINUTES), new Function<Throwable,Directive>() {

				@Override
				public Directive apply(Throwable t) {
					if (t instanceof ArithmeticException) {
						System.out.println("meet ArithmetricException, just resume");
						return SupervisorStrategy.resume();
					} else if (t instanceof NullPointerException) {
						System.out.println("meet null pointer ,restart");
						return SupervisorStrategy.restart();
					} else if (t instanceof IllegalArgumentException) {
						System.out.println("meet illegal argument ,stop");
						return SupervisorStrategy.stop();
					} else {
						return SupervisorStrategy.escalate();
					}
				}
		
	});
	
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
		        .match(Props.class, propsMsg -> {
		          getContext().actorOf((Props)propsMsg,"restartActor");
		        })
		        .build();
	}

}
