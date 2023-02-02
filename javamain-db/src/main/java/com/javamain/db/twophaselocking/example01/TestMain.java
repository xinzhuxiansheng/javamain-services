package com.javamain.db.twophaselocking.example01;

/**
 * @author yzhou
 * @date 2022/12/22
 */
public class TestMain {
    public static void main(String[] args) {
        TwoPhaseLocking pl = new TwoPhaseLocking();
        pl.generateThreeTransation();

        pl.sequenceSchedule();
        System.out.println("************************************************************");
//        pl.concurrencyScheduleWithoutControl();
//        System.out.println("************************************************************");
//        pl.concurrencyScheduleWith2PL();
    }
}
