package com.javamain.akka.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.management.javadsl.AkkaManagement;
import com.typesafe.config.ConfigFactory;

/*
    java -Dakka.remote.artery.canonical.port=2551 -Dakka.remote.artery.canonical.hostname=127.0.0.1 -jar your-app.jar
    java -Dakka.remote.artery.canonical.port=2552 -Dakka.remote.artery.canonical.hostname=127.0.0.1 -jar your-app.jar
 */
public class ClusterMain {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("ClusterSystem", ConfigFactory.load());
        AkkaManagement.get(system).start();
        ActorRef leaderAndMemberListener = system.actorOf(Props.create(LeaderAndMemberListener.class), "LeaderAndMemberListener");
    }
}

