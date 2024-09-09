package com.javamain.akka.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.management.javadsl.AkkaManagement;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/*
    java -Dakka.remote.artery.canonical.port=2551 -Dakka.remote.artery.canonical.hostname=127.0.0.1 -jar your-app.jar
    java -Dakka.remote.artery.canonical.port=2552 -Dakka.remote.artery.canonical.hostname=127.0.0.1 -jar your-app.jar
 */
public class ClusterMain {
    public static void main(String[] args) {

        int port = 2551;

        // Override the configuration of the port
        Config config = ConfigFactory.parseString("akka.cluster.roles = [worker]")
                .withFallback(ConfigFactory.load());
        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        AkkaManagement.get(system).start();

        // 创建事件总线 Actor
//        ActorRef eventBus = system.actorOf(Props.create(EventBus.class), "eventBus");
        //ActorRef leaderAndMemberListener = system.actorOf(
        //        Props.create(LeaderAndMemberListener.class, eventBus), "LeaderAndMemberListener");

        // 创建订阅者 Actor
        ActorRef leaderSubscriber = system.actorOf(Props.create(LeaderSubscriber.class), "leaderSubscriber");
//        eventBus.tell(new Subscribe(subscriber), subscriber);
        ActorRef workerActor = system.actorOf(Props.create(WorkerActor.class), "workerActor");
        System.out.println(workerActor.path().toString());


    }
}

