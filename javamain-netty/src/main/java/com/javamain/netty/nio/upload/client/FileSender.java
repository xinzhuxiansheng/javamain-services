package com.javamain.netty.nio.upload.client;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileSender {
    public static void main(String[] args) {
        String filePath = "D:\\BaiduNetdiskDownload\\uploadfiletest.pdf";
        File file = new File(filePath);
        if (!file.isFile()) {
            System.out.println(false);
            return;
        }

        try (Socket socket = new Socket("127.0.0.1", 9001);
             OutputStream outputStream = socket.getOutputStream();
             RandomAccessFile raf = new RandomAccessFile(file, "r");
             FileChannel fileChannel = raf.getChannel()) {

            byte[] filenameBytes = file.getName().getBytes(StandardCharsets.UTF_8);

            // Send file request
            ByteBuffer buffer = ByteBuffer.allocate(8 + filenameBytes.length);
            buffer.putInt(1);
            buffer.putInt(filenameBytes.length);
            buffer.put(filenameBytes);
            outputStream.write(buffer.array());

            // Send file data
            ByteBuffer fileBuffer = ByteBuffer.allocate(1024);
            int bytesRead;
            while ((bytesRead = fileChannel.read(fileBuffer)) > 0) {
                fileBuffer.flip();
                byte[] fileBytes = new byte[bytesRead];
                fileBuffer.get(fileBytes);

                buffer = ByteBuffer.allocate(12 + filenameBytes.length);
                buffer.putInt(2);
                buffer.putInt(filenameBytes.length);
                buffer.put(filenameBytes);
                buffer.putInt(fileBytes.length);
                outputStream.write(buffer.array());
                outputStream.write(fileBytes);

                fileBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
