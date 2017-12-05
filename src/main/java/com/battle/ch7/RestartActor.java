package com.battle.ch7;


import java.util.Optional;

import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RestartActor extends AbstractActor {
	public enum Msg {
		DONE, RESTART
	}
	
	@Override
	public void preStart() {
		System.out.println("preStart hashcode:" + this.hashCode());
		System.out.println(getSelf().path());
	}

	@Override
	public void postStop() {
		System.out.println("postStop hashcode:" + this.hashCode());
	}
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		super.postRestart(reason);
		System.out.println("postRestart hashcode:" + this.hashCode());
	}
	
	@Override
	public void preRestart(Throwable reason, Optional opt) {
		System.out.println("preRestart hashcode:" + this.hashCode());
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
		        .match(Msg.class, msg -> {
		        	if (msg == Msg.DONE) {
		        		getContext().stop(getSelf());
		        	} else if (msg == Msg.RESTART) {
		        		System.out.println(((Object)null).toString());
		        		double a = 0/0;
		        	}	          
		        })
		        .build();
	}
	
	public static void customerStrategy(ActorSystem system) {
		ActorRef a = system.actorOf(Props.create(Supervisor.class), "Supervisor");
		a.tell(Props.create(RestartActor.class), ActorRef.noSender());
		
		ActorSelection sel = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");
		
		for (int i = 0; i < 100; i++) {
			sel.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
		}
	}
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("lifecycle", ConfigFactory.load("lifecycle.conf"));
		customerStrategy(system);
	}

}
