package com.javamain.akka.cluster.eventbus.eventmessage;

import akka.actor.ActorRef;

public class Subscribe {
    public final ActorRef subscriber;

    public Subscribe(ActorRef subscriber) {
        this.subscriber = subscriber;
    }
}
