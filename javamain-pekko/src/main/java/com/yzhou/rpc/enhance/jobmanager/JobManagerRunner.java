package com.yzhou.rpc.enhance.jobmanager;

import com.yzhou.rpc.enhance.rpc.Configuration;
import com.yzhou.rpc.enhance.rpc.RpcService;
import com.yzhou.rpc.enhance.rpc.RpcUtils;

public class JobManagerRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setProperty("actor.system.name","jobmanager");
        RpcService rpcService = RpcUtils.createRpcService(configuration);
        JobMaster jobMaster = new JobMaster(rpcService,"job_master");
    }
}

