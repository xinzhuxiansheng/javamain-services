package com.yzhou.rpc.enhance.rpc;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorSystem;

public class RpcUtils {

    /**
     * 读取 application.conf，创建 ActorSystem.
     */
    public static RpcService createRpcService(Configuration configuration) {
        // jobmanager /  taskmanager
        String actorSystemName = configuration.getProperty("actor.system.name");
        // 加载 application.conf 配置项
        Config config = ConfigFactory.load();
        ActorSystem actorSystem = ActorSystem.create(actorSystemName, config.getConfig(actorSystemName));
        return new RpcService(actorSystem);
    }
}
