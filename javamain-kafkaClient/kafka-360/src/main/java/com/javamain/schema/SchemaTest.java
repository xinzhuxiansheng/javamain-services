package com.javamain.schema;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import org.apache.avro.Schema;

import java.io.IOException;
import java.util.Arrays;

public class SchemaTest {
    public static void main(String[] args) throws RestClientException, IOException {

        String topic = "avro-tp01";
        String columnType = "value";
        CachedSchemaRegistryClient registryClient =
                new CachedSchemaRegistryClient(
                        Arrays.asList("localhost:8081"), 10);
        String subject = String.format("%s-%s", topic, columnType);
        SchemaMetadata metadata;
        metadata = registryClient.getLatestSchemaMetadata(subject);
        Schema schema = new Schema.Parser().parse(metadata.getSchema());


    }





    public void createSchema(){
//        String subject = "yzhoutp110";
//        String schemaString = "{\"type\":\"record\",\"name\":\"User\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"}]}";
//        Schema avroSchema = new Schema(subject, 1, 1, schemaString);
//        int schemaId = schemaRegistryClient.register(subject, avroSchema);
    }

}
