package com.javamain.kafkaClient.scram;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer01 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"User\",\n" +
                "  \"namespace\": \"com.example\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"id\",\n" +
                "      \"type\": \"int\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"name\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"address\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"ext_field01\",\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        org.apache.avro.Schema.Parser parser = new org.apache.avro.Schema.Parser();
        org.apache.avro.Schema schema = parser.parse(userSchema);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.0.34:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://192.168.0.34:8081");

        // 添加 SCRAM 认证参数
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "SCRAM-SHA-512");
        properties.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required " +
                "username=\"admin\" " +
                "password=\"123456\";");


        org.apache.kafka.clients.producer.Producer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties);
//        Long i = 0L;
//        while (true) {


        GenericRecord record = new GenericData.Record(schema);
        record.put("id", 123);
        record.put("name", "Test Name");
        record.put("address", "Test Address");
        record.put("ext_field01", "Test Field");

        producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp01", record), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (null == recordMetadata) {
                    e.printStackTrace();
                }
            }
        });
        Thread.currentThread().sleep(3000L);
//            i++;
//        }
    }

}
