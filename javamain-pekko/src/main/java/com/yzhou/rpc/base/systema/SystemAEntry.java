package com.yzhou.rpc.base.systema;

import com.yzhou.rpc.base.common.UserFinder;
import org.apache.pekko.actor.ActorRef;

import java.util.concurrent.ExecutionException;
public class SystemAEntry {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GatewayUtil gatewayUtil = new GatewayUtil();
        ActorRef targetRef = gatewayUtil.connect("127.0.0.1", 17338);
        UserFinder userFinder = gatewayUtil.getGateway(UserFinder.class,targetRef);

        String userNameById = userFinder.getUserNameById(100);
        System.out.println(userNameById);

        int ageById = userFinder.getAgeById(100);
        System.out.println(ageById);
    }
}
