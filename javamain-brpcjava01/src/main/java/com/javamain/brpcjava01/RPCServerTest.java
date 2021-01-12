/*
package com.javamain.brpcjava01;

import com.baidu.brpc.server.RpcServer;
import com.baidu.brpc.server.RpcServerOptions;

public class RPCServerTest {

    public static void main(String[] args) {
        int port = 8002;
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }

        RpcServerOptions options = new RpcServerOptions();
        options.setReceiveBufferSize(64 * 1024 * 1024);
        options.setSendBufferSize(64 * 1024 * 1024);
        final RpcServer rpcServer = new RpcServer(port, options);
        rpcServer.registerService(new EchoServiceImpl());
        rpcServer.start();

        // make server keep running
        synchronized (RPCServerTest.class) {
            try {
                RPCServerTest.class.wait();
            } catch (Throwable e) {
            }
        }
    }
}
*/
