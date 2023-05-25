package com.javamain.concurrent.threadorder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class PokerGame {
    public static void main(String[] args) {
        // 创建扑克牌
        List<String> deck = createDeck();
        Collections.shuffle(deck);

        // 将扑克牌放入阻塞队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(deck);

        // 创建 Semaphores
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        Semaphore semaphoreC = new Semaphore(0);
        Semaphore semaphoreD = new Semaphore(0);

        // 创建玩家线程
        Thread playerA = new Player("A", queue, semaphoreA, semaphoreB);
        Thread playerB = new Player("B", queue, semaphoreB, semaphoreC);
        Thread playerC = new Player("C", queue, semaphoreC, semaphoreD);
        Thread playerD = new Player("D", queue, semaphoreD, semaphoreA);

        // 开始游戏
        playerA.start();
        playerB.start();
        playerC.start();
        playerD.start();
    }

    private static List<String> createDeck() {
        String[] suits = {"♠", "♥", "♣", "♦"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        List<String> deck = new LinkedList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(suit + rank);
            }
        }

        return deck;
    }

    static class Player extends Thread {
        private final String name;
        private final BlockingQueue<String> deck;
        private final Semaphore currentSemaphore;
        private final Semaphore nextSemaphore;

        public Player(String name, BlockingQueue<String> deck, Semaphore currentSemaphore, Semaphore nextSemaphore) {
            this.name = name;
            this.deck = deck;
            this.currentSemaphore = currentSemaphore;
            this.nextSemaphore = nextSemaphore;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 13; i++) {
                    currentSemaphore.acquire(); // 请求当前信号量

                    String card = deck.take();
                    System.out.println(name + " 抓到了: " + card);

                    nextSemaphore.release(); // 释放下一个信号量，允许下一个玩家抓牌
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
