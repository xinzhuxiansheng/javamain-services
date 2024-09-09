//package com.javamain.rocketmq.example;
//
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.LocalTransactionState;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.TransactionListener;
//import org.apache.rocketmq.client.producer.TransactionMQProducer;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//
//public class TransactionProducer {
//
//    public static void main(String[] args) throws MQClientException, InterruptedException {
//        // Step 1: 创建一个 TransactionMQProducer 实例
//        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer_group");
//        // Step 2: 设置 NameServer 地址
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        // Step 3: 注册事务监听器
//        producer.setTransactionListener(new TransactionListener() {
//            // yzhou 什么时候执行
//            @Override
//            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//                // 在执行本地事务之前，将事务 ID 和状态插入到数据库中
//                insertTransactionStatus(transactionId, "UNKNOW");
//                try{
//                    // 执行本地事务，例如创建订单记录和更新库存
//                    // createOrder();
//                    // updateInventory();
//
//                    // 如果本地事务成功执行，返回 COMMIT_MESSAGE 以使消息在 RocketMQ 中可见
//                    // 根据业务逻辑的执行结果更新事务状态
//                    updateTransactionStatus(transactionId, "COMMITTED");
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                }catch (Exception e){
//                    // 如果本地事务执行失败，返回 ROLLBACK_MESSAGE 以告知 RocketMQ 丢弃这条消息
//                    // 如果执行失败，更新事务状态
//                    updateTransactionStatus(transactionId, "ROLLED_BACK");
//                    return LocalTransactionState.ROLLBACK_MESSAGE;
//                }
//            }
//
//            @Override
//            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
////                // TODO: 根据 msg.getTransactionId() 检查事务状态，具体根据业务需求实现
////
////                // 如果本地事务已提交，返回 COMMIT_MESSAGE，否则返回 ROLLBACK_MESSAGE
////                return LocalTransactionState.COMMIT_MESSAGE;
//
//                String transactionId = msg.getTransactionId();
//
//                // TODO: 根据 transactionId 查询事务状态，具体根据业务需求实现
//                TransactionStatus status = getTransactionStatus(transactionId);
//
//                if (status == TransactionStatus.COMMITTED) {
//                    // 如果查询到事务已成功提交，返回 COMMIT_MESSAGE
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                } else if (status == TransactionStatus.ROLLED_BACK) {
//                    // 如果查询到事务已回滚，返回 ROLLBACK_MESSAGE
//                    return LocalTransactionState.ROLLBACK_MESSAGE;
//                } else {
//                    // 如果事务状态未知，返回 UNKNOW
//                    return LocalTransactionState.UNKNOW;
//                }
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
//        SendResult sendResult = producer.sendMessageInTransaction(message, null);
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
