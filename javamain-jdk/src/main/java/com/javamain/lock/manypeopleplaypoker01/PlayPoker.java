package com.javamain.lock.manypeopleplaypoker01;

import java.util.LinkedList;

/**
 * @author yzhou
 * @date 2023/2/16
 */
public class PlayPoker implements Runnable {
    private String name;
    private volatile GeneratedPoker generatedPoker;
    LinkedList<Poker> havePokers = null;

    // 锁
    private Object hg = null;
    private Boolean isRunning = false;

    public PlayPoker(String name, GeneratedPoker generatedPoker, Object hg, Boolean isRunning) {
        this.name = name;
        this.generatedPoker = generatedPoker;
        this.havePokers = new LinkedList<>();
        this.hg = hg;
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        while (isRunning) {

            handler();
        }
    }

//    private void waitP() {
//        synchronized (hg) {
//            try {
//                hg.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void notifyP() {
//        synchronized (hg) {
//
//        }
//    }

    private void handler() {
        // 开始前，判断扑克还剩多少
        if (generatedPoker.getLeftNum() <= 0) {
            // TODO 则终止线程
            isRunning = false;
        }

        Poker firstPoker = generatedPoker.mo();
        if (firstPoker == null) {
            // TODO 此处不应该为null
            isRunning = false;
        }
        havePokers.add(firstPoker);
        System.out.println(String.format("%s 摸了一张牌"));
    }
}
