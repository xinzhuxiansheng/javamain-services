//package com.javamain.rocketmq.example;
//
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.*;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//
//public class TransactionProducer02 {
//
//    public static void main(String[] args) throws MQClientException, InterruptedException {
//        // Step 1: 创建一个 TransactionMQProducer 实例
//        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer_group");
//        // Step 2: 设置 NameServer 地址
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        // Step 3: 注册事务监听器
//        producer.setTransactionListener(new TransactionListener() {
//            @Override
//            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//                return LocalTransactionState.COMMIT_MESSAGE;
//            }
//
//            @Override
//            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//                return LocalTransactionState.COMMIT_MESSAGE;
//            }
//        });
//
//        // Step 4: 启动生产者
//        producer.start();
//
//        // Step 5: 构造一个 Message 实例
//        Message message = new Message("transaction_topic", "transaction_tag", "transaction_key", "Hello Transaction Message".getBytes());
//
//        // Step 6: 调用 sendMessageInTransaction 方法发送事务消息
//        TransactionSendResult sendResult = producer.sendMessageInTransaction(message, null);
//        if(sendResult.getSendStatus())
//
//        System.out.printf("Transaction Message Send Result: %s%n", sendResult);
//
//        // 暂停，以便观察事务消息的发送和消费过程
//        Thread.sleep(1000 * 10);
//
//        // Step 7: 在适当的时候关闭生产者
//        producer.shutdown();
//    }
//}
