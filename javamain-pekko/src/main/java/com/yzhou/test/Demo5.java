package com.yzhou.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.apache.pekko.japi.pf.ReceiveBuilder;
import org.apache.pekko.actor.AbstractActor;

public class Demo5 {
    // 演示本地actor通信
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("my-system");
        // pekko://my-system/user/actor-1
        ActorRef actorRef1 = actorSystem.actorOf(Props.create(Actor_a.class), "actor-1");
        //pekko://my-system/user/actor-2
        ActorRef actorRef2 = actorSystem.actorOf(Props.create(Actor_a.class), "actor-2");
        actorRef1.tell(new Msg("你好"),actorRef2);
    }
}


class Actor_a extends AbstractActor{
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Msg.class,this::handleMsg)
                .build();
    }

    public void handleMsg(Msg msg) throws InterruptedException {
        ActorRef sender = getSender();
        System.out.println("收到来自于"+ sender + " 的消息:" + msg.getMsg());
        Thread.sleep(1000);
        sender.tell(new Msg("回应"+msg.getMsg()),getSelf());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Msg{
    private String msg;
}
