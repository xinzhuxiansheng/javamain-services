package com.javamain.lock.manypeopleplaypoker01;

import java.util.LinkedList;

/**
 * @author yzhou
 * @date 2023/2/16
 */
public class GeneratedPoker {
    private LinkedList<Poker> pokerList;
    private Integer sumNum;
    private Integer leftNum;

    public GeneratedPoker(LinkedList<Poker> pokerList, Integer sumNum, Integer leftNum) {
        this.pokerList = pokerList;
        this.sumNum = sumNum;
        this.leftNum = leftNum;
    }

    /**
     * 摸排
     */
    public synchronized Poker mo() {
        if (leftNum <= 0) {
            return null;
        }
        Poker poker = pokerList.poll();
        leftNum--;
        return poker;
    }


    public LinkedList<Poker> getPokerList() {
        return pokerList;
    }

    public void setPokerList(LinkedList<Poker> pokerList) {
        this.pokerList = pokerList;
    }

    public Integer getSumNum() {
        return sumNum;
    }

    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }
}
