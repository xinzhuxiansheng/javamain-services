package com.javamain.flink;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import akka.actor.Props;
import akka.management.javadsl.AkkaManagement;
import com.javamain.flink.httpserver.HttpServer;
import com.javamain.flink.jobstatus.akkacluster.JobStatusManager;
import com.javamain.flink.jobstatus.akkacluster.LeaderSubscriber;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.ExecutionContextExecutor;

/*
    java -Dakka.remote.artery.canonical.port=2551 -Dakka.remote.artery.canonical.hostname=127.0.0.1 -jar your-app.jar
    java -Dakka.remote.artery.canonical.port=2552 -Dakka.remote.artery.canonical.hostname=127.0.0.1 -jar your-app.jar
 */
public class FlinkMain {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("ClusterSystem", ConfigFactory.load());
        AkkaManagement.get(system).start();

        ActorRef jobStatusManager = system.actorOf(
                Props.create(JobStatusManager.class), "JobStatusManager");
        ActorRef leaderSubscriber = system.actorOf(
                Props.create(LeaderSubscriber.class, jobStatusManager), "LeaderSubscriber");


        // Create and start the HTTP server
        ExecutionContextExecutor executor = system.dispatcher();
//        ActorRef serviceManager = system.actorOf(
//                Props.create(ServiceManager.class), "ServiceManager");
        HttpServer.startHttpServer(system, executor);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            CoordinatedShutdown.get(system)
                    .run(CoordinatedShutdown.JvmExitReason$.MODULE$);
        }));
    }

}

