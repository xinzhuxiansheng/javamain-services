package com.javamain.flink.jobstatus.akkacluster;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.javamain.flink.jobstatus.akkacluster.eventmessage.SimpleEventMessages;

public class LeaderSubscriber extends AbstractActor {
    private final LoggingAdapter logger = Logging.getLogger(getContext().getSystem(), this);
    private final Cluster cluster = Cluster.get(getContext().getSystem());
    private ActorRef jobStatusManagerActorRef;

    public LeaderSubscriber(ActorRef jobStatusManagerActorRef) {
        this.jobStatusManagerActorRef = jobStatusManagerActorRef;
    }

    @Override
    public void preStart() {
        // 订阅集群事件，以便及时获知领导者变更
        cluster.subscribe(getSelf(),
                ClusterEvent.ClusterDomainEvent.class,
                ClusterEvent.MemberEvent.class);
    }

    @Override
    public void postStop() {
        // 取消订阅集群事件
        cluster.unsubscribe(getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.LeaderChanged.class, leaderChanged -> {
                    System.out.println("Leader changed to: " + leaderChanged.getLeader());
                    if (leaderChanged.getLeader() == null) {
                        return;
                    }
                    if (leaderChanged.getLeader().equals(cluster.selfAddress())) {
                        jobStatusManagerActorRef.tell(new SimpleEventMessages.StartJobStatusManager(), getSelf());
                    } else {
                        jobStatusManagerActorRef.tell(new SimpleEventMessages.StopJobStatusManager(), getSelf());
                    }
                })
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    // 处理当前集群状态
                    logger.info("Current Cluster State: {}", state);
                }).match(ClusterEvent.MemberUp.class, memberUp -> {
                    // 成员上线事件
                    logger.info("Member is up: {}", memberUp.member());
                }).match(ClusterEvent.MemberRemoved.class, memberRemoved -> {
                    // 成员下线事件
                    logger.info("Member is removed: {}", memberRemoved.member());
                }).build();
    }
}
