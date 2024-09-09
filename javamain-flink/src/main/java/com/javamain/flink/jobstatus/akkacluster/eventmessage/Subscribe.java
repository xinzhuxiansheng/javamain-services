package com.javamain.flink.jobstatus.akkacluster.eventmessage;

import akka.actor.ActorRef;

public class Subscribe {
    public final ActorRef subscriber;

    public Subscribe(ActorRef subscriber) {
        this.subscriber = subscriber;
    }
}
