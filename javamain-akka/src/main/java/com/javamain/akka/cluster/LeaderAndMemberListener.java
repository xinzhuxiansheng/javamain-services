package com.javamain.akka.cluster;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.*;

public class LeaderAndMemberListener extends AbstractActor {
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
