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
public class Actor01 extends UntypedAbstractActor {
    private static final Logger logger = LoggerFactory.getLogger(Actor01.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        logger.info("Actor01...");
        Message data = (Message) message;
        data.setStep("01");
        ActorRef actorRef = this.getContext().actorOf(Props.create(Actor02.class, Actor02::new));
        actorRef.tell(data, actorRef.noSender());
        Thread.sleep(5000);
    }
}
