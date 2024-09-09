package com.javamain.kafkaClient.producer.schema;

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
                "  \"name\": \"Record\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"name\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"age\",\n" +
                "      \"type\": \"long\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"address\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"record\",\n" +
                "        \"namespace\": \"Record\",\n" +
                "        \"name\": \"address\",\n" +
                "        \"fields\": [\n" +
                "          {\n" +
                "            \"name\": \"country\",\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"province\",\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"area\",\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
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
            // String data = i + "样例数据！";
//            GenericRecord avroRecord = new GenericData.Record(schema);
//            avroRecord.put("name", "Alice");
//            avroRecord.put("age", 25 + i);

            // 创建address记录
            GenericRecord addressRecord = new GenericData.Record(schema.getField("address").schema());
            addressRecord.put("country", "SomeCountry");
            addressRecord.put("province", "SomeProvince");
            addressRecord.put("area", "SomeArea");

            // 创建主记录
            GenericRecord avroRecord = new GenericData.Record(schema);
            avroRecord.put("name", "SomeName");
            avroRecord.put("age", 30L);
            avroRecord.put("address", addressRecord);

            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp01", avroRecord), new Callback() {
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
