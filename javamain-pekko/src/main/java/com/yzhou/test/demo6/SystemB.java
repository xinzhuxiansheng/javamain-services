package com.yzhou.test.demo6;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.*;
import org.apache.pekko.japi.pf.ReceiveBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SystemB {
    public static void main(String[] args) {

        HashMap<String, Object> overrides = new HashMap<>();
        overrides.put("pekko.remote.artery.canonical.port", 17336);
        Config config = ConfigFactory.parseMap(overrides).withFallback(ConfigFactory.load());
        ActorSystem actorSystem = ActorSystem.create("system-b", config);
        ActorRef actorRef = actorSystem.actorOf(Props.create(SystemB_Actor.class), "b-actor");
        actorRef.tell(new Start(), ActorRef.noSender());
    }
}

class SystemB_Actor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Start.class, this::start)
                .match(SystemMsg.class, this::handleSystemMsg)
                .build();
    }

    public void start(Start start) throws ExecutionException, InterruptedException {
        // 去给systemA中的 a-actor发送一个SystemMsg消息
        ActorSelection actorSelection = getContext().actorSelection("pekko://system-a@127.0.0.1:17338/user/a-actor");
        CompletableFuture<ActorRef> completableFuture = actorSelection.resolveOne(Duration.ofSeconds(1)).toCompletableFuture();
        ActorRef actorRef = completableFuture.get();
        actorRef.tell(new SystemMsg("我是系统B的actor"), getSelf());
    }

    public void handleSystemMsg(SystemMsg msg) {
        System.out.println("B系统收到了a系统的消息: " + msg.getMsg());
    }
}

class Start {

}
