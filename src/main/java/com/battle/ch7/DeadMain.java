package com.battle.ch7;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class DeadMain {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("DeadWatch", ConfigFactory.load("samplehello.conf"));
		ActorRef worker = system.actorOf(Props.create(MyWork.class), "worker");
		system.actorOf(Props.create(WatchActor.class, worker), "watcher");
		worker.tell(MyWork.Msg.WORKING,ActorRef.noSender());
		worker.tell(MyWork.Msg.DONE,ActorRef.noSender());
		worker.tell(PoisonPill.getInstance(),ActorRef.noSender());
	}

}
