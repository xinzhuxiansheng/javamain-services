package com.javamain.netty.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用ByteBuffer和FileChannel将文件内容读读取出来，并打印到控制台
 */
public class NIOFileChannel03 {
    private static final Logger logger = LoggerFactory.getLogger(NIOFileChannel03.class);

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("D:\\code\\yzhou\\javamain-services\\javamain-netty\\src\\main\\resources\\test.txt");
        FileChannel fileChannelRead = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("test03.txt");
        FileChannel fileChannelWrite = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            byteBuffer.clear();
            int read = fileChannelRead.read(byteBuffer);
            if (read == -1) { //表示读完
                break;
            }
            byteBuffer.flip();
            fileChannelWrite.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}

