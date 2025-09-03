package com.javamain.forkjoinpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestMain {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
//    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
//    ForkJoinPool handlerThreadPool = new ForkJoinPool(4000);
    long startTime = System.currentTimeMillis();
    List<String> dataList = new ArrayList<>();
    for (int i = 0; i < 40000; i++) {
      dataList.add("test" + i);
    }

    ExecutorService executor = Executors.newFixedThreadPool(400);
    CountDownLatch latch = new CountDownLatch(dataList.size()); // 计数器=任务数

    for (String data : dataList) {
      executor.submit(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + " : " + data);
          Thread.sleep(200);
        }catch (Exception e){
          e.printStackTrace();
        }finally {
          latch.countDown(); // 任务完成，计数器-1
        }
      });
    }

    latch.await(); // 阻塞直到计数器归零
    executor.shutdown();


    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
  }
}
