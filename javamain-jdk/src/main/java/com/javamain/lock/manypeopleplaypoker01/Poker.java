package com.javamain.lock.manypeopleplaypoker01;

/**
 * @author yzhou
 * @date 2023/2/16
 */
public class Poker {
    private String color;
    private String num;

    public Poker(String color, String num) {
        this.color = color;
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return color + num;
    }
}
