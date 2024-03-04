package com.yzhou.test.demo6;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.apache.pekko.japi.pf.ReceiveBuilder;
import org.apache.pekko.actor.AbstractActor;

public class SystemA {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        // listening on address [pekko://system-a@127.0.0.1:17338]
        ActorSystem actorSystem = ActorSystem.create("system-a", config);
        ActorRef actorRef = actorSystem.actorOf(Props.create(SystemA_Actor.class), "a-actor");
    }
}

class SystemA_Actor extends AbstractActor{
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(SystemMsg.class,this::handleSystemMsg)
                .build();
    }

    public void handleSystemMsg(SystemMsg msg){
        ActorRef sender = getSender();
        System.out.println("来自 "+ sender +" 消息: "  + msg.getMsg());
        sender.tell(new SystemMsg("你好"),getSelf());
    }
}

