package com.yzhou.rpc.enhance.taskmanager;

import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.*;
import org.apache.pekko.japi.pf.ReceiveBuilder;

public class TestMain {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("a", ConfigFactory.load().getConfig("jobmanager"));
        Address address = actorSystem.provider().getDefaultAddress();
        ActorRef actorRef = actorSystem.actorOf(Props.create(TestActor.class), "xx");
        System.out.println(actorRef.path().toStringWithAddress(address));
    }
}

class TestActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, this::a)
                .build();
    }

    public void a(String x) {
    }
}
