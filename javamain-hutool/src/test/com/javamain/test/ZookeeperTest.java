package com.javamain.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperTest.class);
    private ZooKeeper zooKeeper;
    private CountDownLatch connectionLatch = new CountDownLatch(1);

    @Before
    public void initZooKeeperClient() throws IOException, InterruptedException {
        String host_port= "xxx.xxx.66.205:2181";
        zooKeeper = new ZooKeeper(host_port, 2000, new Watcher() {
            public void process(WatchedEvent we) {
                if (we.getState() == Event.KeeperState.SyncConnected) {
                    connectionLatch.countDown();
                }
            }
        });

        connectionLatch.await();
    }

    @Test
    public void create() throws InterruptedException {
        String path = "/yzhoujavatest";

        List<ACL> acl = new ArrayList<>();
        zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int i, String s, Object o, String s1) {
                System.out.println("ok");
            }
        },null);

        Thread.sleep(3000);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //忽略此方法
    }
}
