package com.javamain.flink.jobstatus.akkacluster;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/*
    Worker Actor 主要负责从 Leader承接 Task
 */
public class WorkerActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, word-> log.info("Node {} receives: {}", getSelf().path().toSerializationFormat(), word))
                .build();
    }
}
