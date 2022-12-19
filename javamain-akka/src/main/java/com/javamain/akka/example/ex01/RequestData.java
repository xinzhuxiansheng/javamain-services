package com.javamain.akka.example.ex01;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yzhou
 * @date 2022/12/15
 */
public class RequestData {
    private static final Logger logger = LoggerFactory.getLogger(RequestData.class);

    private static ScheduledExecutorService jobDataExecutor;
    static ActorSystem actorSystem = null;

    public RequestData() {
        jobDataExecutor = Executors.newSingleThreadScheduledExecutor();
        jobDataExecutor.scheduleAtFixedRate(this::requestData, 5, 60, TimeUnit.SECONDS);

        actorSystem = ActorSystem.create("yzhouSystem", createConfig());
//        actorSystem = ActorSystem.create("yzhouSystem");
    }

    // 非常重要的参数
    private Config createConfig() {
        Map<String, Object> map = new HashMap<String, Object>();
        // forkjoinpool默认线程数 max(min(cpu线程数 * parallelism-factor, parallelism-max), 8)
        map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-factor", "1");
        map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-max", "1");

//        map.put("akka.loglevel", "ERROR");
//        map.put("akka.stdout-loglevel", "ERROR");
//
//        //开启akka远程调用
//        map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider");
//
//        List<String> remoteTransports = new ArrayList<String>();
//        remoteTransports.add("akka.remote.netty.tcp");
//        map.put("akka.remote.enabled-transports", remoteTransports);
//
//        map.put("akka.remote.netty.tcp.hostname", host);
//        map.put("akka.remote.netty.tcp.port", port);
//
//        map.put("akka.remote.netty.tcp.maximum-frame-size", 100 * 1024 * 1024);
//
//        logger.info("akka.remote.netty.tcp.hostname="+map.get("akka.remote.netty.tcp.hostname"));
//        logger.info("akka.remote.netty.tcp.port="+map.get("akka.remote.netty.tcp.port"));

        return ConfigFactory.parseMap(map);
    }

    private void requestData() {
        logger.info("开始定时任务...");
        for (int i = 0; i < 30; i++) {
            Message message = new Message("name" + i, "age" + i);
            ActorRef jobRootRef = actorSystem.actorOf(Props.create(Actor01.class, Actor01::new));
            jobRootRef.tell(message, jobRootRef.noSender());
        }
    }
}
