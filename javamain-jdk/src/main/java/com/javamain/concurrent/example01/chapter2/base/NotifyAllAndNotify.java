package com.javamain.concurrent.example01.chapter2.base;

/*
    这段代码仅为了证明当线程调用共享对象的wait()方法时，当前线程只会释放当前共享对象的锁，当前线程
    持有的其他共享对象的监视器锁并不会释放  所在页 P10
 */
public class NotifyAllAndNotify {
    //1. 创建共享资源
    private static volatile Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {

        //2. 创建线程A，模拟wait
        Thread threadA = new Thread(new Runnable() {
            public void run() {

                //2.1 获取resourceA共享资源的监视器锁
                synchronized (resourceA) {
                    System.out.println("threadA get resourceA lock");
                    try {
                        System.out.println("threadA begin wait");
                        resourceA.wait();
                        System.out.println("threadA end wait");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //3. 创建线程B,模拟wait
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                synchronized (resourceA) {
                    System.out.println("threadB get resourceA lock");
                    try {

                        System.out.println("threadB begin wait");
                        resourceA.wait();
                        System.out.println("threadB end wait");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        //3. 创建线程C,通知等待的线程
        Thread threadC = new Thread(new Runnable() {
            public void run() {
                synchronized (resourceA) {
                    System.out.println("threadC begin notify");
                    //resourceA.notifyAll();
                    resourceA.notify();
                }
            }
        });

        //4. 启动线程
        threadA.start();
        threadB.start();
        Thread.sleep(1000);
        threadC.start();

        //4. 等待线程结束
        threadA.join();
        threadB.join();
        threadC.join();
        System.out.println("main over");
    }
}
