package com.javamain.akka.cluster.eventbus;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.javamain.akka.cluster.eventbus.eventmessage.EventMessage;
import com.javamain.akka.cluster.eventbus.eventmessage.Subscribe;
import com.javamain.akka.cluster.eventbus.eventmessage.Unsubscribe;

import java.util.HashSet;
import java.util.Set;

public class EventBus extends AbstractActor {
    private Set<ActorRef> subscribers = new HashSet<>();
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Subscribe.class, subscribe -> {
                    subscribers.add(subscribe.subscriber);
                    getContext().watch(subscribe.subscriber); // Optional: Watch for termination
                })
                .match(Unsubscribe.class, unsubscribe -> {
                    subscribers.remove(unsubscribe.subscriber);
                    getContext().unwatch(unsubscribe.subscriber); // Optional: Stop watching
                })
                .match(EventMessage.class, eventMessage -> {
                    // 处理事件消息并分发给订阅者
                    //getContext().getChildren().forEach(child -> child.tell(eventMessage, getSelf()));
                    subscribers.forEach(subscriber -> subscriber.tell(eventMessage, getSelf()));
                })
                .build();
    }
}
