package com.javamain.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Futures {
    public static void addCallback(Future<Integer> future, FutureCallback futureCallback,
                                   ExecutorService executorService){
        Runnable callback = new Runnable(){
            @Override
            public void run() {
                Integer result = null;
                try{
                    result = future.get();
                    futureCallback.onSuccess(result);
                }catch(InterruptedException e){
                    futureCallback.onFailure(e);
                } catch (ExecutionException e) {
                    futureCallback.onFailure(e);
                }
            }
        };
    }
}
