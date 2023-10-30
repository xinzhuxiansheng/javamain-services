package com.javamain.kafkaClient.producer.schema;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Producer07 {
    public static void main(String[] args) throws InterruptedException {
        String userSchema = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"MyRecord\",\n" +
                "  \"namespace\": \"com.example\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"field01\",\n" +
                "      \"type\": [\n" +
                "        \"null\",\n" +
                "        {\n" +
                "          \"type\": \"record\",\n" +
                "          \"name\": \"InnerRecord\",\n" +
                "          \"fields\": [\n" +
                "            {\"name\": \"id\", \"type\": \"int\"},\n" +
                "            {\"name\": \"name\", \"type\": \"string\"},\n" +
                "            {\n" +
                "              \"name\": \"info\",\n" +
                "              \"type\": {\n" +
                "                \"type\": \"record\",\n" +
                "                \"name\": \"InfoRecord\",\n" +
                "                \"fields\": [\n" +
                "                  {\"name\": \"aaa\", \"type\": \"string\"},\n" +
                "                  {\"name\": \"bbb\", \"type\": \"string\"}\n" +
                "                ]\n" +
                "              }\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"field02\",\n" +
                "      \"type\": [\"null\", {\"type\": \"array\", \"items\": \"int\"}],\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"field03\",\n" +
                "      \"type\": [\n" +
                "        \"null\",\n" +
                "        {\n" +
                "          \"type\": \"array\",\n" +
                "          \"items\": {\n" +
                "            \"type\": \"record\",\n" +
                "            \"name\": \"ArrayItemRecord\",\n" +
                "            \"fields\": [\n" +
                "              {\"name\": \"id\", \"type\": \"int\"},\n" +
                "              {\"name\": \"name\", \"type\": \"string\"}\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"default\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"field04\",\n" +
                "      \"type\": [\"null\", {\"type\": \"map\", \"values\": \"string\"}],\n" +
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

            // 2. 根据模板创建内嵌的record数据
            GenericRecord infoRecord = new GenericData.Record(schema.getField("field01").schema().getTypes().get(1).getField("info").schema());
            infoRecord.put("aaa", "value1");
            infoRecord.put("bbb", "value2");

            // 3. 创建field01的record数据
            GenericRecord field01Record = new GenericData.Record(schema.getField("field01").schema().getTypes().get(1));
            field01Record.put("id", 123);
            field01Record.put("name", "testName");
            field01Record.put("info", infoRecord);

            // 4. 创建整个record
            GenericRecordBuilder recordBuilder = new GenericRecordBuilder(schema);
            recordBuilder.set("field01", field01Record);

            // 5. 创建field02的数组数据
            recordBuilder.set("field02", Arrays.asList(1, 2, 3));

            // 6. 创建field03的数组数据，该数组包含records
            GenericRecord arrayItemRecord1 = new GenericData.Record(schema.getField("field03").schema().getTypes().get(1).getElementType());
            arrayItemRecord1.put("id", 1);
            arrayItemRecord1.put("name", "name1");

            GenericRecord arrayItemRecord2 = new GenericData.Record(schema.getField("field03").schema().getTypes().get(1).getElementType());
            arrayItemRecord2.put("id", 2);
            arrayItemRecord2.put("name", "name2");

            recordBuilder.set("field03", Arrays.asList(arrayItemRecord1, arrayItemRecord2));

            // 7. 创建field04的map数据
            Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            recordBuilder.set("field04", map);

            // 8. 构建record对象
            GenericRecord record = recordBuilder.build();

            producer.send(new ProducerRecord<String, GenericRecord>("yzhoutp09", record), new Callback() {
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
