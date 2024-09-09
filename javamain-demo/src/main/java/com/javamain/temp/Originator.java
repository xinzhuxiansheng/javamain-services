package com.javamain.temp;

public class Originator {
    private int state;

    public void setState(int state) {
        this.state = state;

        // 创建memento
        // 在放入Caretaker容器中
    }
    public int getState() {
        return state;
    }
}
