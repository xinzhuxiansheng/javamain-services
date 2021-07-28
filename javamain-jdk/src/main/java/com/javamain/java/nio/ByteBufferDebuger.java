package com.javamain.java.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

public class ByteBufferDebuger {
    private static final Logger logger = LoggerFactory.getLogger(ByteBufferDebuger.class);

    public static void main(String[] args) {

//        CharBuffer buffer = CharBuffer.allocate(10);
//        buffer.put('你');
//        buffer.put('好');
////  经过标记后，会持续记住此位置
//        buffer.position(0).mark();
//        while (buffer.hasRemaining()) {
//            System.out.println(buffer.get());
//            //  mark将会跳转到上次标记的位置
//            buffer.reset();
//        }


//        //Kafka ByteBuffer
//        int size = 10;
//        //以size为空间大小的创建ByteBuffer对象
//        ByteBuffer bf01 = ByteBuffer.allocate(size);
//        bf01.put((byte)4);
//        logger.info("position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//
//        String a = "asdfasf";
//        bf01.put(a.getBytes());
//        logger.info("position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());


//        int size01 = 6;
//        //以size为空间大小的创建ByteBuffer对象
//        ByteBuffer bf01 = ByteBuffer.allocate(size01);
//        bf01.putInt(5);
//        bf01.put((byte)1);
//        logger.info("write position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//
//        bf01.flip();
//        logger.info("buffer 翻转之后");
//        logger.info("read position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//
//        logger.info("从buffer中读取数据");
//        bf01.mark();
//        bf01.getInt();
//        logger.info("read position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//        bf01.reset();
//
//        logger.info("read position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//        while(bf01.hasRemaining()){
//            System.out.println(bf01.get());
//        }

        //Kafka ByteBuffer
//        int size = 10;
//        //以size为空间大小的创建ByteBuffer对象
//        ByteBuffer bf02 = ByteBuffer.allocate(size);
//        bf02.putInt(Integer.MAX_VALUE);
//        logger.info("position: {} , limit: {} , capacity: {}",bf02.position(),bf02.limit(),bf02.capacity());


        // 示例9.1 duplicate()
//        int size01 = 6;
//        //以size为空间大小的创建ByteBuffer对象
//        ByteBuffer bf01 = ByteBuffer.allocate(size01);
//        bf01.putInt(5);
//        logger.info("bf01 position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//
//        ByteBuffer bf02 = bf01.duplicate();
//        logger.info("duplicate bf02 position: {} , limit: {} , capacity: {}",bf02.position(),bf02.limit(),bf02.capacity());
//
//        logger.info("bf02 添加数据");
//        bf01.put((byte) 1);
//        logger.info("bf01 position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//        logger.info("bf02 position: {} , limit: {} , capacity: {}",bf02.position(),bf02.limit(),bf02.capacity());
//
//        bf01.flip();
//        bf02.flip();
//        logger.info("read bf01 数据1: {} , 数据2: {} .",bf01.getInt(),bf01.get());
//        logger.info("read bf02 数据1: {} , 数据2: {} .",bf02.getInt(),bf02.get());


        // 示例9.2
//        int size01 = 6;
//        //以size为空间大小的创建ByteBuffer对象
//        ByteBuffer bf01 = ByteBuffer.allocate(size01);
//        bf01.putInt(5);
//        bf01.put((byte) 1);
//        logger.info("bf01 position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//        bf01.position(4);
//        logger.info("bf01 设置新position后  position: {} , limit: {} , capacity: {}",bf01.position(),bf01.limit(),bf01.capacity());
//        ByteBuffer bf02 = bf01.slice();
//        logger.info("bf02 position: {} , limit: {} , capacity: {}",bf02.position(),bf02.limit(),bf02.capacity());
//        logger.info("bf02 value: {}",bf02.get());

        // 示例9.4
        int size01 = 6;
        //以size为空间大小的创建ByteBuffer对象
        ByteBuffer bf01 = ByteBuffer.allocate(size01);
        bf01.put((byte) 0);
        bf01.put((byte) 1);
        bf01.put((byte) 2);
        bf01.put((byte) 3);
        logger.info("bf01 position: {} , limit: {} , capacity: {}", bf01.position(), bf01.limit(), bf01.capacity());
        logger.info("bf01 value: {}", Arrays.toString(bf01.array()));
        ByteBuffer bf02 = bf01.compact();
        logger.info("bf02 position: {} , limit: {} , capacity: {}", bf02.position(), bf02.limit(), bf02.capacity());
        logger.info("bf02 value: {}", Arrays.toString(bf02.array()));


    }
}
