package com.javamain.kafkaClient.producer.schema;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer06 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{"
                + "\"type\":\"record\","
                + "\"name\":\"UserInfo\","
                + "\"namespace\":\"com.example\","
                + "\"fields\":["
                + "  { \"name\":\"id\", \"type\":\"int\" },"
                + "  { \"name\":\"name\", \"type\":\"string\" },"
                + "  { \"name\":\"age\", \"type\":\"int\" },"
                + "  { \"name\":\"duo\", \"type\":[\"null\", {"
                + "      \"type\":\"record\","
                + "      \"name\":\"SubRecord\","
                + "      \"fields\":["
                + "        { \"name\":\"subfield1\", \"type\":\"string\" },"
                + "        { \"name\":\"subfield2\", \"type\":\"int\" }"
                + "      ]"
                + "    }], \"default\":null }"
                + "]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.64.xxx:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://192.168.64.xxx:8081");
        org.apache.kafka.clients.producer.Producer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties);
        Long i = 0L;
        while (true) {

            // 创建 Avro 记录实例
            GenericRecord user = new GenericData.Record(schema);
            user.put("id", 123);
            user.put("name", "John Doe");
            user.put("age", 25);

            // 创建 duo 字段的子记录
            GenericRecord subRecord = new GenericData.Record(
                    schema.getField("duo").schema().getTypes().get(1));
            subRecord.put("subfield1", "Example");
            subRecord.put("subfield2", 10);

            user.put("duo", subRecord);

            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp08", user), new Callback() {
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
