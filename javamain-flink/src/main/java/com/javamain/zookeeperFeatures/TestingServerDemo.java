package com.javamain.zookeeperFeatures;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.curator.test.Timing;

/**
 * @author yzhou
 * @date 2022/6/1
 */
public class TestingServerDemo {

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        Timing timing = new Timing();
        CuratorFramework curator = CuratorFrameworkFactory
                .builder()
                .connectString(server.getConnectString())
                .sessionTimeoutMs(timing.session())
                .connectionTimeoutMs(timing.connection())
                .retryPolicy(new RetryOneTime(1))
                .build();
    }
}
