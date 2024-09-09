package com.javamain.akka.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class RouterClientTest {
    public static void main( String[] args ) throws InterruptedException {
        Config config = ConfigFactory.load();
        ActorSystem system = ActorSystem.create("ClientSystem", config);
        ActorSelection toFind = system.
                actorSelection("akka://ClusterSystem@127.0.0.1:2551/user/leaderSubscriber");
        int counter = 0;
        while(true){
            toFind.tell("hello "+counter++, ActorRef.noSender());
            System.out.println("Finish telling");
            Thread.sleep(2000);
        }
    }
}
