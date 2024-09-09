package com.yzhou.test;

import lombok.Data;
import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.apache.pekko.japi.pf.ReceiveBuilder;
import org.apache.pekko.pattern.Patterns;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeoutException;

public class Demo {
    public static void main(String[] args) throws InterruptedException, TimeoutException {

        ActorSystem actorSystem = ActorSystem.create("actor_system_demo");
        ActorRef actorRef = actorSystem.actorOf(Props.create(MyActor.class), "my-actor");

        // 非阻塞，发完即走
        actorRef.tell(new Start(), ActorRef.noSender());

        ProcessData processData = new ProcessData();
        processData.setA(10);
        processData.setB(20);
        processData.setCalcType("+");

        // ask 是有返回的消息发送方法
        Future<Object> future = Patterns.ask(actorRef, processData, 1000);
        int result = (int) Await.result(future, Duration.create("1s"));
        System.out.println("收到结果:" + result);
    }
}


class MyActor extends AbstractActor {

    @Override
    public Receive createReceive() {

        return ReceiveBuilder.create()
                .match(Start.class, this::handleStart)
                .match(ProcessData.class, this::handleProcessData)
                .build();
    }


    private void handleStart(Start start) throws InterruptedException {
        System.out.println("我要启动了");
        //Thread.sleep(5000);
        System.out.println("我启动完成了");
    }

    private void handleProcessData(ProcessData processData) {

        int a = processData.getA();
        int b = processData.getB();
        String calcType = processData.getCalcType();

        int result;
        switch (calcType) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            default:
                result = a * b;
        }

        ActorRef sender = getSender();
        sender.tell(result, getSelf());

    }

}

class Start {

}

@Data
class ProcessData {
    private int a;
    private int b;
    private String calcType;
}
