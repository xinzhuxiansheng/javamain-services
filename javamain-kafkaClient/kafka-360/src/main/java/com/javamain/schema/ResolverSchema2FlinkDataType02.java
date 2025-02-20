package com.javamain.schema;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import org.apache.avro.Schema;

import java.io.IOException;
import java.util.Arrays;

public class ResolverSchema2FlinkDataType02 {
    public static void main(String[] args) throws RestClientException, IOException {

        String topic = "avro_tp02";
        String columnType = "value";
        CachedSchemaRegistryClient registryClient =
                new CachedSchemaRegistryClient(
                        Arrays.asList("http://localhost:7081"), 10);
        String subject = String.format("%s-%s", topic, columnType);
        SchemaMetadata metadata;
        metadata = registryClient.getLatestSchemaMetadata(subject);
        Schema schema = new Schema.Parser().parse(metadata.getSchema());
        
        parseSchema(schema, "");
    }

    private static void parseSchema(Schema schema, String indent) {
        switch (schema.getType()) {
            case RECORD:
                System.out.println(indent + "Record: " + schema.getFullName());
                for (Schema.Field field : schema.getFields()) {
                    parseSchema(field.schema(), indent + "  ");
                }
                break;
            case ARRAY:
                System.out.println(indent + "Array: " + schema.getElementType());
                parseSchema(schema.getElementType(), indent + "  ");
                break;
            case MAP:
                System.out.println(indent + "Map: " + schema.getValueType());
                parseSchema(schema.getValueType(), indent + "  ");
                break;
            case ENUM:
                System.out.println(indent + "Enum: " + schema.getFullName());
                break;
            case UNION:
                System.out.println(indent + "Union");
                for (Schema s : schema.getTypes()) {
                    parseSchema(s, indent + "  ");
                }
                break;
            default:
                System.out.println(indent + "Type: " + schema.getType());
        }
    }


    public void createSchema() {
//        String subject = "yzhoutp110";
//        String schemaString = "{\"type\":\"record\",\"name\":\"User\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"}]}";
//        Schema avroSchema = new Schema(subject, 1, 1, schemaString);
//        int schemaId = schemaRegistryClient.register(subject, avroSchema);
    }

}
