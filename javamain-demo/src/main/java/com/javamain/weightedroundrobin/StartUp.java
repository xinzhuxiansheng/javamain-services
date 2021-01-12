package com.javamain.weightedroundrobin;

public class StartUp {
    public static void main(String[] args) {
        /**
         * 假设有三个服务器权重配置如下：
         * server A  weight = 4 ;
         * server B  weight = 3 ;
         * server C  weight = 2 ;
         */
        Node serverA = new Node("serverA", 4);
        Node serverB = new Node("serverB", 3);
        Node serverC = new Node("serverC", 2);

        WeightedRoundRobin weightedRoundRobin = new WeightedRoundRobin(serverA,serverB ,serverC);
        for (int i = 0; i < 9; i++) {
            Node i1 = weightedRoundRobin.select();
            System.out.println(i1.getServerName());
        }
    }
}
