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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Producer02 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"Record\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"id\",\n" +
                "      \"type\": \"long\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"name\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"date\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"obj\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"record\",\n" +
                "        \"namespace\": \"Record\",\n" +
                "        \"name\": \"obj\",\n" +
                "        \"fields\": [\n" +
                "          {\n" +
                "            \"name\": \"time1\",\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"str\",\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"lg\",\n" +
                "            \"type\": \"long\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"arr\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"array\",\n" +
                "        \"items\": {\n" +
                "          \"type\": \"record\",\n" +
                "          \"namespace\": \"Record\",\n" +
                "          \"name\": \"arr\",\n" +
                "          \"fields\": [\n" +
                "            {\n" +
                "              \"name\": \"f1\",\n" +
                "              \"type\": \"string\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"f2\",\n" +
                "              \"type\": \"long\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"time\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"timestamp\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"map\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"record\",\n" +
                "        \"namespace\": \"Record\",\n" +
                "        \"name\": \"map\",\n" +
                "        \"fields\": [\n" +
                "          {\n" +
                "            \"name\": \"flink\",\n" +
                "            \"type\": \"long\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        org.apache.avro.Schema.Parser parser = new org.apache.avro.Schema.Parser();
        org.apache.avro.Schema schema = parser.parse(userSchema);

        Properties properties = new Properties();
        //properties.put("bootstrap.servers", "192.168.64.xxx:9092");
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        //properties.put("schema.registry.url", "http://192.168.64.xxx:8081");
        properties.put("schema.registry.url", "http://localhost:7081");
        org.apache.kafka.clients.producer.Producer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties);
        Long i = 0L;
        while (true) {
            // String data = i + "样例数据！";

            // Create top level Generic Record
            GenericRecord avroRecord = new GenericData.Record(schema);

            avroRecord.put("id", 123456L);
            avroRecord.put("name", "Test Record");
            avroRecord.put("date", "2023-07-27");
            avroRecord.put("time", "11:00:00");
            avroRecord.put("timestamp", "2023-07-27T11:00:00Z");

            // Create sub record obj
            Schema objSchema = schema.getField("obj").schema();
            GenericRecord subRecordObj = new GenericData.Record(objSchema);
            subRecordObj.put("time1", "10:00:00");
            subRecordObj.put("str", "Inner String");
            subRecordObj.put("lg", 789L);
            avroRecord.put("obj", subRecordObj);

            // Create sub record map
            Schema mapSchema = schema.getField("map").schema();
            GenericRecord subRecordMap = new GenericData.Record(mapSchema);
            subRecordMap.put("flink", 123L);
            avroRecord.put("map", subRecordMap);

            // Create array arr
            Schema arraySchema = schema.getField("arr").schema().getElementType();
            List<GenericRecord> arrList = new ArrayList<>();
            GenericRecord arrRecord1 = new GenericData.Record(arraySchema);
            arrRecord1.put("f1", "Array String 1");
            arrRecord1.put("f2", 456L);
            arrList.add(arrRecord1);
            GenericRecord arrRecord2 = new GenericData.Record(arraySchema);
            arrRecord2.put("f1", "Array String 2");
            arrRecord2.put("f2", 789L);
            arrList.add(arrRecord2);
            avroRecord.put("arr", arrList);


            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp02", avroRecord), new Callback() {
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
