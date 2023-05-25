package com.javamain.concurrent.example01.chapter2.base;

/*
    这段代码仅为了说明： 当一个线程调用共享对象的wait()方法被阻塞挂起后，如果其他线程中断了该线程，
    则该线程会抛出InterruptedException异常并返回。  所在页 P11
 */
public class WaitNotifyInterupt {
    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        //1.创建线程
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("---begin---");
                    //阻塞当前线程
                    obj.wait();
                    System.out.println("---end---");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadA.start();

        Thread.sleep(1000);

        System.out.println("---begin interrupt threadA---");
        threadA.interrupt();
        System.out.println("---end interrupt threadA---");
    }
}
