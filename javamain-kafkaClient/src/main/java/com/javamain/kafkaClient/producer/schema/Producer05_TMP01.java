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

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Properties;

public class Producer05_TMP01 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"xsj_kafka_avro_0511\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"id\",\n" +
                "      \"type\": \"int\",\n" +
                "      \"doc\": \"学生id\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"name\",\n" +
                "      \"type\": [\"null\", \"float\"],\n" +
                "      \"doc\": \"学生姓名\",\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"a1\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"bytes\",\n" +
                "        \"logicalType\": \"decimal\",\n" +
                "        \"precision\": 12,\n" +
                "        \"scale\": 2\n" +
                "      },\n" +
                "      \"doc\": \"验证精度 标度\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"b2\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"long\",\n" +
                "        \"logicalType\": \"timestamp-millis\"\n" +
                "      },\n" +
                "      \"doc\": \"验证精度 标度\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"b1\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"int\",\n" +
                "        \"logicalType\": \"time-millis\"\n" +
                "      },\n" +
                "      \"doc\": \"验证精度 标度\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "43.138.2.215:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://43.138.2.215:8081");
        org.apache.kafka.clients.producer.Producer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties);
        Long i = 0L;
        while (true) {
            // String data = i + "样例数据！";

            GenericRecord avroRecord = new GenericData.Record(schema);
            avroRecord.put("id", 1);
            avroRecord.put("name", 23.45f);
            BigDecimal decimalValue = new BigDecimal("123.45");
            ByteBuffer byteBufferValue = ByteBuffer.wrap(decimalValue.unscaledValue().toByteArray());
            avroRecord.put("a1", byteBufferValue);
            avroRecord.put("b2", System.currentTimeMillis());
            avroRecord.put("b1", 45096000);  // 12:34:56 in milliseconds

            producer.send(new ProducerRecord<String, GenericRecord>("xsj_kafka_avro_0511", avroRecord), new Callback() {
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
