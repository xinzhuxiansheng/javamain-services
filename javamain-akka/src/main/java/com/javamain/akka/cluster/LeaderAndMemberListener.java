package com.javamain.akka.cluster;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaderAndMemberListener extends AbstractActor {
    private static final Logger logger = LoggerFactory.getLogger(LeaderAndMemberListener.class);
    private ActorRef eventBus;
    public LeaderAndMemberListener(ActorRef eventBus){
        this.eventBus = eventBus;
    }
    private final Cluster cluster = Cluster.get(getContext().getSystem());

    @Override
    public void preStart() {
        cluster.subscribe(getSelf(), ClusterDomainEvent.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(LeaderChanged.class, leaderChanged -> {
                    System.out.println("Leader changed to: " + leaderChanged.getLeader());
                    //eventBus.tell(new EventMessage("New leader is " + leaderChanged.leader()), self());

                })
                .match(MemberUp.class, memberUp -> {
                    System.out.println("Member is Up: " + memberUp.member());
                })
                .match(UnreachableMember.class, unreachable -> {
                    System.out.println("Member detected as unreachable: " + unreachable.member());
                })
                .match(MemberRemoved.class, memberRemoved -> {
                    System.out.println("Member is Removed: " + memberRemoved.member());
                })
                .match(CurrentClusterState.class, state -> {
                    System.out.println("Current members: " + state.members());
                    System.out.println("Current leader: " + state.getLeader());
                })
                .build();
    }
}
