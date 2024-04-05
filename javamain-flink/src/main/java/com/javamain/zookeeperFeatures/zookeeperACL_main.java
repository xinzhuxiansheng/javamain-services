//package com.javamain.zookeeperFeatures;
//
//import com.google.common.base.Charsets;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.api.ACLProvider;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;
//
//import java.util.List;
//
//public class zookeeperACL_main {
//
//    public static void main(String[] args) {
//
//        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
//                .connectString("xx.yy.96.90:2181")
//                .retryPolicy(new ExponentialBackoffRetry(1000,3,3000))
//                .namespace("Yzhou02");
//
//        builder.authorization("digest", "xxxxhome000000:xxxxhome001".getBytes(Charsets.UTF_8))
//                .aclProvider(new ACLProvider() {
//                    @Override
//                    public List<ACL> getDefaultAcl() {
//                        return ZooDefs.Ids.CREATOR_ALL_ACL;
//                    }
//                    @Override
//                    public List<ACL> getAclForPath(final String path) {
//                        return ZooDefs.Ids.CREATOR_ALL_ACL;
//                    }
//                });
//
//        CuratorFramework client = builder.build();
//        client.start();
//
//        System.out.println("zk 启动成功");
//
//        //参考官网文档:http://curator.apache.org/curator-framework/index.html
//        //创建node
//        String data1 = "创建node";
//        try {
//            client.create().forPath("/create",data1.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//}
