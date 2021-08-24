package com.javamain.longpolling.server;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;

public class AsyncContextWrapper {
    private AsyncContext asyncContext;
    private boolean timeout;
    private Long createTime;

    public AsyncContextWrapper(AsyncContext asyncContext, boolean timeout) {
        this.asyncContext = asyncContext;
        this.timeout = timeout;
        if (timeout == true) {
            this.createTime = System.currentTimeMillis();
        }

        asyncContext.setTimeout(30000);

        //设置监听
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });
    }
}
