package com.javamain.lock.manypeopleplaypoker01;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author yzhou
 * @date 2023/2/16
 */
public class PokerTools {
    public static LinkedList<Poker> generatingCards() {
        String[] colors = {"黑桃", "方块", "梅花", "红桃"};
        String[] nums = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        LinkedList pokers = new LinkedList();
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                pokers.add(new Poker(colors[i], nums[j]));
            }
        }
        pokers.add(new Poker("小王", "黑"));
        pokers.add(new Poker("大王", "红"));
        //洗牌
        LinkedList shuffledpokers = new LinkedList<Poker>();
        while (shuffledpokers.size() < pokers.size()) {
            Random x = new Random();
            Poker poke = (Poker) pokers.get(x.nextInt(pokers.size()));
            if (!shuffledpokers.contains(poke)) {
                shuffledpokers.add(poke);
            }
        }
        System.out.println("洗牌前：" + pokers);
        System.out.println("洗牌后：" + shuffledpokers);
        return shuffledpokers;
    }
}
