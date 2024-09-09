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

public class Producer06_TMP01 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"kafka_avro_defi0605\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"id\",\n" +
                "      \"type\": \"long\",\n" +
                "      \"doc\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"col_num\",\n" +
                "      \"type\": [\"null\", \"long\"],\n" +
                "      \"doc\": \"\",\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"col_var\",\n" +
                "      \"type\": [\"null\", \"string\"],\n" +
                "      \"doc\": \"\",\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"col_bool\",\n" +
                "      \"type\": [\"null\", \"boolean\"],\n" +
                "      \"doc\": \"\",\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"col_date\",\n" +
                "      \"type\": [\"null\", {\"type\": \"int\", \"logicalType\": \"date\"}],\n" +
                "      \"doc\": \"\",\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"col_time\",\n" +
                "      \"type\": {\"type\": \"long\", \"logicalType\": \"timestamp-millis\"},\n" +
                "      \"doc\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"comm\",\n" +
                "      \"type\": [\"null\", \"string\"],\n" +
                "      \"doc\": \"\",\n" +
                "      \"default\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
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
            // String data = i + "样例数据！";

            GenericRecord avroRecord = new GenericData.Record(schema);
            avroRecord.put("id", 1L);
            avroRecord.put("col_num", 100L);
            avroRecord.put("col_var", "Sample String");
            avroRecord.put("col_bool", true);
            avroRecord.put("col_date", 19428);  // 2023-06-05 in days since 1970-01-01
            avroRecord.put("col_time", System.currentTimeMillis());
            avroRecord.put("comm", "Sample Comment");


            producer.send(new ProducerRecord<String, GenericRecord>("kafka_avro_defi0605", avroRecord), new Callback() {
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
