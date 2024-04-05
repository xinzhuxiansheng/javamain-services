package com.javamain.zookeeperFeatures.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Curator LeaderLatch
 *
 * @author yzhou
 * @date 2022/6/27
 */
public class ServerLeaderExample {
    private static final Logger logger = LoggerFactory.getLogger(ServerLeaderExample.class);
    private static final String PATH = "/example/server-leader";
    private static final String CONNECT_IP_PORT = "test.zk.com:2181";

    public static void main(String[] args) {
        CuratorFramework client = null;
        LeaderLatch leaderLatch = null;
        try {
            client = CuratorFrameworkFactory.builder().connectString(CONNECT_IP_PORT).sessionTimeoutMs(5000)
                    .connectionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            client.start();
            leaderLatch = new LeaderLatch(client, PATH);
            ZkJobLeaderLatchListener listener = new ZkJobLeaderLatchListener();
            leaderLatch.addListener(listener);
            leaderLatch.start();

            Thread.sleep(50000);

            if (leaderLatch.hasLeadership()) {
                System.out.println("------close----------begin----");
                leaderLatch.close();
                System.out.println("------close----------end----");
            }
            Thread.sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(leaderLatch);
            CloseableUtils.closeQuietly(client);
        }


    }

    static class ZkJobLeaderLatchListener implements LeaderLatchListener {

        public void isLeader() {
            System.out.println("成为leader:");
        }

        public void notLeader() {
            System.out.println("不是leader:");
        }
    }
}
