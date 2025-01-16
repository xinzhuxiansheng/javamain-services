package com.javamain.db;

import com.javamain.db.bigqueue01.BigArrayImpl;
import com.javamain.db.bigqueue01.IBigArray;
import com.javamain.db.bigqueue01.utils.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBigArray {
    final String storageDir = "E:\\Code\\Java\\javamain-services\\broker";

    @Test
    public void testSizeBigArray() throws IOException {
        IBigArray bigArray = new BigArrayImpl(storageDir, "array");

        for (int i = 0; i < 10; i++) {
            String item = String.valueOf(i);
            long index = bigArray.append(item.getBytes());
        }

        long size = bigArray.size();
        Assertions.assertEquals(10, size);
    }

    @Test
    public void testGetBigArray() throws IOException, InterruptedException {
        IBigArray bigArray = new BigArrayImpl(storageDir, "array");
        long size = bigArray.size();
        System.out.println("size: " + size);
        for (int i = 0; i < 10; i++) {
            byte[] bytes = bigArray.get(i);
            System.out.println(new String(bytes));
        }
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testDiv(){
        long previousIndexPageIndex = Calculator.div(0, 17);
        System.out.println(previousIndexPageIndex);
    }
}
