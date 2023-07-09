package com.javamain.demo.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用ByteBuffer和FileChannel将内容写入文件
 */
public class NIOFileChannel01 {
    private static final Logger logger = LoggerFactory.getLogger(NIOFileChannel01.class);

    public static void main(String[] args) throws IOException {
        String str = "hello world";
        FileOutputStream fileOut = new FileOutputStream("D:\\code\\yzhou\\javamain-services\\javamain-netty\\src\\main\\resources\\test.txt");
        FileChannel fileChannel = fileOut.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOut.close();
    }
}
