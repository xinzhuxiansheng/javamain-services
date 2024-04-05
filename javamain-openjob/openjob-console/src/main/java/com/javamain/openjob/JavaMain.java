package com.javamain.openjob;

import io.openjob.worker.OpenjobWorker;

public class JavaMain {
    public static void main(String[] args) {
        try {
            OpenjobWorker openjobWorker = new OpenjobWorker();
            openjobWorker.init();

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}