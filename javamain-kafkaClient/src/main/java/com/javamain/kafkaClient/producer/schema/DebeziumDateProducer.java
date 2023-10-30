package com.javamain.kafkaClient.producer.schema;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDate;
import java.util.Properties;

public class DebeziumDateProducer {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"MyRecord\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"AUTO_ID\",\n" +
                "      \"type\": \"int\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"POSTING_DATE\",\n" +
                "      \"type\": [\n" +
                "        \"null\",\n" +
                "        {\n" +
                "          \"type\": \"int\",\n" +
                "          \"connect.version\": 1,\n" +
                "          \"connect.name\": \"io.debezium.time.Date\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"default\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        org.apache.avro.Schema.Parser parser = new org.apache.avro.Schema.Parser();
        org.apache.avro.Schema schema = parser.parse(userSchema);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.64.xxx:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://192.168.64.xxx:8081");
        org.apache.kafka.clients.producer.Producer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties);
        Long i = 0L;
        while (true) {

            // 创建address记录
            GenericRecord record = new GenericData.Record(schema);
            record.put("AUTO_ID", 123);

            // 假设我们要将当前日期作为 POSTING_DATE
            // 因为 "io.debezium.time.Date" 类型表示的是从 epoch 日期开始的天数，所以我们这样计算
            LocalDate now = LocalDate.now(); // 获取当前日期
            long daysSinceEpoch = now.toEpochDay(); // 计算从 epoch 日期到现在的天数

            // 设置 POSTING_DATE 字段
            record.put("POSTING_DATE", (int)daysSinceEpoch); // 需要转换为 int

            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp05", record), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null == recordMetadata) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.currentThread().sleep(3000L);
            i++;
        }
    }

}
