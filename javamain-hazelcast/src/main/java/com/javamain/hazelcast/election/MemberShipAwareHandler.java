package com.javamain.hazelcast.election;

import com.hazelcast.internal.services.MembershipAwareService;
import com.hazelcast.internal.services.MembershipServiceEvent;

public class MemberShipAwareHandler implements MembershipAwareService {
    @Override
    public void memberAdded(MembershipServiceEvent membershipServiceEvent) {

    }

    @Override
    public void memberRemoved(MembershipServiceEvent membershipServiceEvent) {

    }
}
