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

import java.util.*;

public class Producer03 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"Employee\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"name\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"age\",\n" +
                "      \"type\": \"int\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"skills\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"map\",\n" +
                "        \"values\": \"string\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"address\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"map\",\n" +
                "        \"values\": {\n" +
                "          \"type\": \"record\",\n" +
                "          \"name\": \"AddressRecord\",\n" +
                "          \"fields\": [\n" +
                "            {\n" +
                "              \"name\": \"street\",\n" +
                "              \"type\": \"string\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"city\",\n" +
                "              \"type\": \"string\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
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

            // Create a new record
            GenericRecord avroRecord = new GenericData.Record(schema);
            avroRecord.put("name", "John Doe");
            avroRecord.put("age", 30);

            // Create the 'skills' map
            Map<String, String> skills = new HashMap<>();
            skills.put("Java", "expert");
            skills.put("Python", "intermediate");
            skills.put("SQL", "beginner");
            avroRecord.put("skills", skills);

            // Create the 'address' map
            Map<String, GenericRecord> address = new HashMap<>();

            // Create the 'home' address record
            Schema addressSchema = schema.getField("address").schema().getValueType();
            GenericRecord homeAddress = new GenericData.Record(addressSchema);
            homeAddress.put("street", "123 Main St");
            homeAddress.put("city", "Springfield");
            address.put("home", homeAddress);

            // Create the 'work' address record
            GenericRecord workAddress = new GenericData.Record(addressSchema);
            workAddress.put("street", "456 Market St");
            workAddress.put("city", "Shelbyville");
            address.put("work", workAddress);

            avroRecord.put("address", address);

            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp03", avroRecord), new Callback() {
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
