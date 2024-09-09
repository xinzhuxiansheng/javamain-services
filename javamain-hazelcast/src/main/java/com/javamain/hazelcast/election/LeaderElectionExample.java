package com.javamain.hazelcast.election;

import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class LeaderElectionExample implements MembershipListener {
    private HazelcastInstance hazelcastInstance;
    private String leaderAddress;

    public static void main(String[] args) {
        LeaderElectionExample example = new LeaderElectionExample();
        example.start();
    }

    public void start() {
        hazelcastInstance = Hazelcast.newHazelcastInstance();
        hazelcastInstance.getCluster().addMembershipListener(this);
        leaderAddress = hazelcastInstance.getCluster().getMembers().iterator().next().getSocketAddress().toString();

        if (hazelcastInstance.getCluster().getLocalMember().getSocketAddress().toString().equals(leaderAddress)) {
            // This node is the leader, do leader-specific tasks here
            System.out.println("print leader");
        }
    }

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        if (membershipEvent.getMember().getSocketAddress().toString().compareTo(leaderAddress) < 0) {
            // A new member has joined that has an older address, it becomes the new leader
            leaderAddress = membershipEvent.getMember().getSocketAddress().toString();
            if (hazelcastInstance.getCluster().getLocalMember().getSocketAddress().toString().equals(leaderAddress)) {
                // This node is the new leader, do leader-specific tasks here

            }
        }
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        if (membershipEvent.getMember().getSocketAddress().toString().equals(leaderAddress)) {
            // The leader has left the cluster, a new leader will be elected
            leaderAddress = hazelcastInstance.getCluster().getMembers().iterator().next().getSocketAddress().toString();
            if (hazelcastInstance.getCluster().getLocalMember().getSocketAddress().toString().equals(leaderAddress)) {
                // This node is the new leader, do leader-specific tasks here
            }
        }
    }
}
