package com.javamain.akka.route;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SlaveActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, word-> log.info("Node {} receives: {}", getSelf().path().toSerializationFormat(), word))
                .build();
    }
}
