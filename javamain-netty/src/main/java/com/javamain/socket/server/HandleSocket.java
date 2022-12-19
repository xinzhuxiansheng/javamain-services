package com.javamain.socket.server;

import com.javamain.socket.common.Encoder;
import com.javamain.socket.common.Message;
import com.javamain.socket.common.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author yzhou
 * @date 2022/12/19
 */
public class HandleSocket implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HandleSocket.class);

    private Socket socket;
    BufferedReader reader;
    BufferedWriter writer;

    public HandleSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        InetSocketAddress address = (InetSocketAddress) socket.getRemoteSocketAddress();
        logger.info("Client connection info {}", address.getAddress().getHostAddress() + ":" + address.getPort());
        try {
            Transfer t = new Transfer(socket);

            while (true) {
                try {
                    byte[] requestData = t.receive();
                    Message message = Encoder.decode(requestData);
                    System.out.println("Client: " + new String(message.getData()));
                    String respStr = "消息已接受到，请指示";
                    t.send(Encoder.encode(new Message(respStr.getBytes(StandardCharsets.UTF_8))));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
