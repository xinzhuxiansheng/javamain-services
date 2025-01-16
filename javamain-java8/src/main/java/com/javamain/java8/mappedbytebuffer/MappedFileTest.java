package com.javamain.java8.mappedbytebuffer;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MappedFileTest {
    private static final String filePath = "E:/Code/Java/javamain-services/javamain-java8/file/00000000";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 内存映射文件
//        loadFileTest();

        testWriteAndReadFile();
    }

    public static void loadFileTest() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        MappedFile mappedFile = new MappedFile();
        mappedFile.loadFileInMMap(filePath, 0, 1024 * 1024 * size);
        System.out.println("映射了" + size + "m的空间");
        TimeUnit.SECONDS.sleep(20);
        System.out.println("释放内存");
        mappedFile.clean();
        TimeUnit.SECONDS.sleep(10000);
    }

    public static void testWriteAndReadFile() throws IOException {
        MappedFile mappedFile = new MappedFile();
        mappedFile.loadFileInMMap(filePath, 0, 1024 * 1024 * 100);
        System.out.println("映射了100m的空间");
        String str = "Hi MappedByteBuffer! :)";
        byte[] content = str.getBytes();
        mappedFile.writeContent(content);
        byte[] readContent = mappedFile.readContent(0, content.length);
        System.out.println(new String(readContent));
    }

}

