package com.javamain.akka.cluster;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, word -> {
                    log.info("WorkerActor {} receives: {}", getSelf().path().toSerializationFormat(), word);
                })
                .build();
    }
}
