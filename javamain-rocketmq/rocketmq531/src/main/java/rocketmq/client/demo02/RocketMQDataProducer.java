package rocketmq.client.demo02;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import java.nio.charset.StandardCharsets;

public class RocketMQDataProducer {
  public static void main(String[] args) throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
    producer.setNamesrvAddr("192.168.0.201:9876");
    producer.start();

    String[] testData = {
            "{\"id\": 1, \"address\": \"北京市海淀区中关村大街\"}",
            "{\"id\": \"user_2\", \"address\": \"上海市浦东新区张江高科技园区\"}",
            "{\"id\": 3, \"address\": \"广东省深圳市南山区科技园\"}"
    };

    for (String data : testData) {
      Message msg = new Message(
              "yzhoutpjson01",
              "",
              data.getBytes(StandardCharsets.UTF_8)
      );
      producer.send(msg);
      System.out.println("Sent: " + data);
    }

    producer.shutdown();
  }
}
