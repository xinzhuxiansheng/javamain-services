package com.javamain.rpc.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ZookeeperUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperUtil.class);
    private ZooKeeper zooKeeper;
    private String connStr;
    private static final int sessionTimeout = 15000;

    public ZookeeperUtil(String connStr) {
        try {
            zooKeeper = new ZooKeeper(connStr, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            logger.error("connect zk failed!", e);
        }
    }

}
