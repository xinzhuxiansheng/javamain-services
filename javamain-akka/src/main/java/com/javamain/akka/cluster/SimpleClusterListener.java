package com.javamain.akka.cluster;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;

public class SimpleClusterListener extends AbstractActor {
    Cluster cluster = Cluster.get(getContext().getSystem());

    @Override
    public void preStart() {
        cluster.subscribe(getSelf(), ClusterEvent.ClusterDomainEvent.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    System.out.println("Member is Up: " + mUp.member());
                })
                .match(ClusterEvent.UnreachableMember.class, unreachable -> {
                    System.out.println("Member detected as unreachable: " + unreachable.member());
                })
                .match(ClusterEvent.MemberRemoved.class, mRemoved -> {
                    System.out.println("Member is Removed: " + mRemoved.member());
                })
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    System.out.println("Current members: " + state.members());
                })
                .build();
    }
}
