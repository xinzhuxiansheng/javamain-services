package com.javamain.flink.jobstatus.akkacluster;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.javamain.flink.jobstatus.akkacluster.eventmessage.Subscribe;
import com.javamain.flink.jobstatus.akkacluster.eventmessage.Unsubscribe;

import java.util.HashSet;
import java.util.Set;

public class EventBus extends AbstractActor {
    private Set<ActorRef> subscribers = new HashSet<>();
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Subscribe.class, subscribe -> {
                    subscribers.add(subscribe.subscriber);
                    getContext().watch(subscribe.subscriber);
                })
                .match(Unsubscribe.class, unsubscribe -> {
                    subscribers.remove(unsubscribe.subscriber);
                    getContext().unwatch(unsubscribe.subscriber);
                })
//                .match(EventMessage.class, eventMessage -> {
//                    // 处理事件消息并分发给订阅者
//                    subscribers.forEach(subscriber -> subscriber.tell(eventMessage, getSelf()));
//                })
                .build();
    }
}
