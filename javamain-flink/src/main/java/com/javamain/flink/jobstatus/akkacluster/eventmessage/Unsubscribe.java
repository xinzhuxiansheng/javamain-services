package com.javamain.flink.jobstatus.akkacluster.eventmessage;

import akka.actor.ActorRef;

public class Unsubscribe {
    public final ActorRef subscriber;

    public Unsubscribe(ActorRef subscriber) {
        this.subscriber = subscriber;
    }
}
