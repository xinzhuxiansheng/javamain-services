package com.javamain.akka.example.ex01;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzhou
 * @date 2022/12/15
 */
public class Actor02 extends UntypedAbstractActor {
    private static final Logger logger = LoggerFactory.getLogger(Actor02.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        logger.info("Actor02...");
        Message data = (Message) message;
        data.setStep("02");

        Thread.sleep(5000);
    }
}
