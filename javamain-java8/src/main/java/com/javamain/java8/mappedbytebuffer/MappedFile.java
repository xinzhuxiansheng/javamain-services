package com.javamain.java8.mappedbytebuffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * MappedByteBuffer 操作类
 */
public class MappedFile {
    private File file;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel fileChannel;

    /**
     * 将文件内存映射
     *
     * @param filePath    文件路径
     * @param startOffset 开始映射的 offset
     * @param mappedSize  映射的体积
     * @throws IOException
     */
    public void loadFileInMMap(String filePath, int startOffset, int mappedSize) throws IOException {
        file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("filePath is " + filePath + " inValid");
        }
        fileChannel = new RandomAccessFile(file, "rw").getChannel();
        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, startOffset, mappedSize);
    }

    /**
     * 支持从文件的指定 offset 开始读取内容
     *
     * @param readOffset
     * @param size
     * @return
     */
    public byte[] readContent(int readOffset, int size) {
        mappedByteBuffer.position(readOffset);
        byte[] content = new byte[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            byte b = mappedByteBuffer.get(readOffset + i);
            content[j++] = b;
        }
        return content;
    }

    /**
     * 高性能写入磁盘
     */
    public void writeContent(byte[] content) {
        this.writeContent(content, false);
    }


    /**
     * 写入数据到磁盘当中
     */
    public void writeContent(byte[] content, boolean force) {
        mappedByteBuffer.put(content);
        if (force) {
            mappedByteBuffer.force();
        }
    }

    public void clean(){
        if(mappedByteBuffer == null || !mappedByteBuffer.isDirect() || mappedByteBuffer.capacity() ==0 ){
            return;
        }
        invoke(invoke(viewed(mappedByteBuffer),"cleaner"),"clean");
    }

    private Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = method(target, methodName, args);
                    method.setAccessible(true);
                    return method.invoke(target);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    private Method method(Object target, String methodName, Class<?>[] args)
            throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }

    private ByteBuffer viewed(ByteBuffer buffer) {
        String methodName = "viewedBuffer";
        Method[] methods = buffer.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("attachment")) {
                methodName = "attachment";
                break;
            }
        }

        ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
        if (viewedBuffer == null)
            return buffer;
        else
            return viewed(viewedBuffer);
    }
}

