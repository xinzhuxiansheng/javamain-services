package com.javamain.netty.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用ByteBuffer和FileChannel将文件内容读读取出来，并打印到控制台
 */
public class NIOFileChannel02 {
    private static final Logger logger = LoggerFactory.getLogger(NIOFileChannel02.class);

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\code\\yzhou\\javamain-services\\javamain-netty\\src\\main\\resources\\test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        fileChannel.read(byteBuffer);
        logger.info(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
