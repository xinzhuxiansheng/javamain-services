package com.javamain.lock.manypeopleplaypoker01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yzhou
 * @date 2023/2/16
 */
public class StartPlay {
    public static ArrayBlockingQueue getmoQueue = new ArrayBlockingQueue(4);

    public static void main(String[] args) {
        // 随机生成一副扑克牌
        LinkedList<Poker> pokerList = PokerTools.generatingCards();
        GeneratedPoker generatedPoker = new GeneratedPoker(pokerList,
                pokerList.size(),
                pokerList.size());

        // 荷官
        Object hg = new Object();
        // 构建四个人
        List<PlayPoker> playPokerList = new ArrayList<>();
        // 初始化四个人摸排队列
        for (int i = 1; i < 5; i++) {
            PlayPoker p = new PlayPoker(String.valueOf(i), generatedPoker, hg, false);
            playPokerList.add(p);
            getmoQueue.add(String.valueOf(i));
        }



    }
}
