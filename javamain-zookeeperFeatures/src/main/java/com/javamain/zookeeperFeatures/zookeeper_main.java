//package com.javamain.zookeeperFeatures;
//
//import com.google.common.base.Charsets;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.api.ACLProvider;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;
//import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//
//public class zookeeper_main {
//
//    public static void main(String[] args) throws NoSuchAlgorithmException {
//
////        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
////                .connectString("vm01.com:2181")
////                .retryPolicy(new ExponentialBackoffRetry(1000,3,3000))
////                .namespace("testnamespace");
////        CuratorFramework client = builder.build();
////        client.start();
////
////        System.out.println("zk 启动成功");
////
////        //注册数据：
////        client.create().creatingParentsIfNeeded();
//
//
//        String s = DigestAuthenticationProvider.generateDigest("super:admin");
//        System.out.println(s);
//
//
//
//
//     //   System.setProperty("zookeeper.sasl.clientconfig",   "Client");
//     //   System.setProperty("zookeeper.sasl.client",   "true");
//
////        builder.authorization("digest", "xxxxhome:xxxxhome001".getBytes(Charsets.UTF_8))
////                .aclProvider(new ACLProvider() {
////
////                    @Override
////                    public List<ACL> getDefaultAcl() {
////                        return ZooDefs.Ids.CREATOR_ALL_ACL;
////                    }
////
////                    @Override
////                    public List<ACL> getAclForPath(final String path) {
////                        return ZooDefs.Ids.CREATOR_ALL_ACL;
////                    }
////                });
//
////
////        //参考官网文档:http://curator.apache.org/curator-framework/index.html
////        //创建node
////        String data1 = "创建node03";
////        try {
////            client.create().forPath("/create",data1.getBytes());
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
//////        try {
//////            client.delete().deletingChildrenIfNeeded().forPath("/create");
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////        }
//
//    }
//
//}
