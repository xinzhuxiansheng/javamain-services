package com.javamain.db.bigqueue01.core;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MappedPageImpl implements IMappedPage {

    private String fileFullPath;
    private FileChannel fileChannel;
    private MappedByteBuffer mappedByteBuffer;
    private ByteBuffer readByteBuffer;

    public MappedPageImpl(String fileFullPath, FileChannel fileChannel, MappedByteBuffer mappedByteBuffer) {
        this.fileFullPath = fileFullPath;
        this.fileChannel = fileChannel;
        this.mappedByteBuffer = mappedByteBuffer;
        this.readByteBuffer = mappedByteBuffer.slice();
    }

    public byte[] readContent(int pos, int length) {
        ByteBuffer readBuf = readByteBuffer.slice();
        readBuf.position(pos);
        byte[] readBytes = new byte[length];
        readBuf.get(readBytes);
        return readBytes;
    }

    /**
     * 仅用于读取
     *
     * @param pos
     * @return
     */
    public ByteBuffer setPosReturnSliceBuffer(int pos) {
        ByteBuffer readBuf = readByteBuffer.slice();
        readBuf.position(pos);
        return readBuf;
    }

    public ByteBuffer setPosReturnSelfBuffer(int pos) {
        mappedByteBuffer.position(pos);
        return mappedByteBuffer;
    }

    public void flush(){
        mappedByteBuffer.force();
    }

    /**
     * 释放mmap内存占用
     */
    public void clean() {
        if (mappedByteBuffer == null || !mappedByteBuffer.isDirect() || mappedByteBuffer.capacity() == 0)
            return;
        invoke(invoke(viewed(mappedByteBuffer), "cleaner"), "clean");
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
