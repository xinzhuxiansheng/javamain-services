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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Producer04 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"ComplexRecord\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"string_field\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"long_field\",\n" +
                "      \"type\": \"long\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"int_field\",\n" +
                "      \"type\": \"int\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"map_field\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"map\",\n" +
                "        \"values\": \"string\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"array_field\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"array\",\n" +
                "        \"items\": {\n" +
                "          \"type\": \"record\",\n" +
                "          \"name\": \"ArrayItem\",\n" +
                "          \"fields\": [\n" +
                "            {\n" +
                "              \"name\": \"item_string\",\n" +
                "              \"type\": \"string\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"item_int\",\n" +
                "              \"type\": \"int\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"array_field2\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"array\",\n" +
                "        \"items\": \"string\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"time_field\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"long\",\n" +
                "        \"logicalType\": \"timestamp-millis\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.64.112:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://192.168.64.112:8081");
        org.apache.kafka.clients.producer.Producer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties);
        Long i = 0L;
        while (true) {
            // String data = i + "样例数据！";

            GenericRecord avroRecord = new GenericData.Record(schema);

            avroRecord.put("string_field", "test");
            avroRecord.put("long_field", 123L);
            avroRecord.put("int_field", 456);

            // Construct map field
            Map<String, String> mapField = new HashMap<>();
            mapField.put("key1", "value1");
            mapField.put("key2", "value2");
            avroRecord.put("map_field", mapField);

            // Construct array field of records
            Schema arrayItemSchema = schema.getField("array_field").schema().getElementType();
            GenericRecord arrayItem1 = new GenericData.Record(arrayItemSchema);
            arrayItem1.put("item_string", "item1");
            arrayItem1.put("item_int", 1);
            GenericRecord arrayItem2 = new GenericData.Record(arrayItemSchema);
            arrayItem2.put("item_string", "item2");
            arrayItem2.put("item_int", 2);
            avroRecord.put("array_field", Arrays.asList(arrayItem1, arrayItem2));

            // Construct array field of strings
            avroRecord.put("array_field2", Arrays.asList("string1", "string2"));

            avroRecord.put("time_field", System.currentTimeMillis());

            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp04", avroRecord), new Callback() {
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
