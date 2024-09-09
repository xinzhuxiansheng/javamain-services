package com.javamain.akka.route;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.Address;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;

import java.util.ArrayList;

public class MasterActor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Router router = new Router(new RoundRobinRoutingLogic(), new ArrayList<>());
    private Cluster cluster = Cluster.get(getContext().system());
    boolean isReady = false;
    private static final String SLAVE_PATH = "/user/slaveActor";

    @Override
    public void preStart() throws Exception {
        cluster.subscribe(self(), ClusterEvent.MemberEvent.class, ClusterEvent.ReachabilityEvent.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg->{
                    log.info("Master got: {}", msg);
                    if(!isReady)
                        log.warning("Is not ready yet!");
                    else {
                        log.info("Routee size: {}", router.routees().length());
                        router.route(msg, getSender());
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp->{
                    if(mUp.member().hasRole("slave")) {
                        Address address = mUp.member().address();
                        String path = address + SLAVE_PATH;
                        ActorSelection selection = getContext().actorSelection(path);
                        router = router.addRoutee(selection);
                        isReady=true;
                        log.info("New routee is added!");
                    }
                })
                .match(ClusterEvent.MemberRemoved.class, mRemoved->{
                    router = router.removeRoutee(getContext().actorSelection(mRemoved.member().address()+SLAVE_PATH));
                    log.info("Routee is removed");
                })
                .match(ClusterEvent.UnreachableMember.class, mRemoved-> {
                    router = router.removeRoutee(getContext().actorSelection(mRemoved.member().address() + SLAVE_PATH));
                    log.info("Routee is removed");
                })
                .build();
    }

//    public static void main(String[] args) {
//        int port = 2551;
//
//        // Override the configuration of the port
//        Config config =
//                ConfigFactory.parseString(
//                                "akka.remote.netty.tcp.port=" + port + "\n" +
//                                        "akka.remote.artery.canonical.port=" + port)
//                        .withFallback(
//                                ConfigFactory.parseString("akka.cluster.roles = [master]"))
//                        .withFallback(ConfigFactory.load());
//
//        ActorSystem system = ActorSystem.create("ClusterSystem", config);
//        ClusterHttpManagement.get(system);
//        AkkaManagement.get(system).start();
//        system.actorOf(Props.create(MasterActor.class), "masterActor");
//    }
}
