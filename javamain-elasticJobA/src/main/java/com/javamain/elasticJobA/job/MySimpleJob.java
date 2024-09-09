package com.javamain.elasticJobA.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        Integer shardingTotalCount =  shardingContext.getShardingTotalCount();
        System.out.println("总的分片数"+shardingTotalCount);

        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("0");
                break;
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;

        }
    }
}
