package com.javamain.concurrent;

import java.util.concurrent.*;

public class FutureTaskDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS
                , new LinkedBlockingDeque<>());
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getResult();
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Futures.addCallback(future, new FutureCallback() {
            @Override
            public void onSuccess(Object result) {
                // 获取到正确结果
                handleResult((Integer) result);
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.println("异步任务执行失败，异常结果为：" + e.getMessage());
            }
        }, executor);

        doSthElse();
    }

    private static void handleResult(Integer result) {
        int ret = 100 + result;
        System.out.println("上一个任务结果+100：" + result + "+100=" + ret);
    }

    /**
     * 获取结果
     */
    private static Integer getResult() throws InterruptedException {
        Thread.sleep(2000);
        int result = (int) (Math.random() * 100);
        if (result >= 0 && result <= 50) {
            return result;
        } else {
            throw new RuntimeException();
        }
    }

    private static void doSthElse() {
        System.out.println("主线程继续执行其他任务");
    }
}
