package com.yzhou.rpc.base.systema;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSelection;
import org.apache.pekko.actor.ActorSystem;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GatewayUtil {
    ActorSystem actorSystem;

    public GatewayUtil() {
        HashMap<String, Object> overrides = new HashMap<>();
        overrides.put("pekko.remote.artery.canonical.port", 17336);
        Config config = ConfigFactory.parseMap(overrides).withFallback(ConfigFactory.load());
        actorSystem = ActorSystem.create("system-a", config);
    }

    public ActorRef connect(String hostname, int port) throws ExecutionException, InterruptedException {
        ActorSelection actorSelection = actorSystem.actorSelection("pekko://system-b@" + hostname + ":" + port + "/user/system-b-actor");
        CompletableFuture<ActorRef> completableFuture = actorSelection.resolveOne(Duration.ofSeconds(1)).toCompletableFuture();
        ActorRef targetActorRef = completableFuture.get();
        return targetActorRef;
    }

    public <T> T getGateway(Class<?> interfaceCls,ActorRef targetActorRef) {
        T o = (T) Proxy.newProxyInstance(
                GatewayUtil.class.getClassLoader(),
                new Class[]{interfaceCls},
                new SystemAInvocationHandler(targetActorRef)
        );
        return o;
    }
}
