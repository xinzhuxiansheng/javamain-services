package com.javamain.akka.example.ex03;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author yzhou
 * @date 2022/12/13
 */
public class App extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.context().system(), this.getClass());

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(Long.class, msg -> {
            log.info("received long: {}", msg);
        }).build();
    }

//    public static void main(String[] args) {
//        ActorSystem system = ActorSystem.create("sequences");
//        var seqRef = system.actorOf(SequencesActor.props(), "sequences");
//        var mineRef = system.actorOf(Props.create(App.class), "ask");
//        seqRef.tell("next", mineRef);
//    }

    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("sequences");
        var seqRef = system.actorOf(SequencesActor.props(), "sequences");
        var mineRef = system.actorOf(Props.create(App.class), "ask");
        while (true) {
            seqRef.tell("next", mineRef);
            Thread.sleep(500);
        }
    }
}
