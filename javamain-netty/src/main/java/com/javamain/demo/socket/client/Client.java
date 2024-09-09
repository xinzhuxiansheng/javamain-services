package com.javamain.demo.socket.client;

import com.javamain.demo.socket.common.Transfer;
import com.javamain.demo.socket.common.Encoder;
import com.javamain.demo.socket.common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author yzhou
 * @date 2022/12/19
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        Scanner sc = null;
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            // 工具类
            Transfer transfer = new Transfer(socket);
            sc = new Scanner(System.in);
            while (true) {
                System.out.print("client:");
                String statStr = sc.nextLine();
                if ("exit".equals(statStr)) {
                    break;
                }
                try {
                    Message resp = request(transfer, statStr.getBytes());
                    System.out.println(new String(resp.getData()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("Client端发生异常", e);
        } finally {
            sc.close();
        }
    }

    private static Message request(Transfer transfer, byte[] requestData) throws Exception {
        Message requestMsg = new Message(requestData);
        transfer.send(Encoder.encode(requestMsg));
        byte[] respData = transfer.receive();
        Message respMsg = Encoder.decode(respData);
        return respMsg;
    }
}
