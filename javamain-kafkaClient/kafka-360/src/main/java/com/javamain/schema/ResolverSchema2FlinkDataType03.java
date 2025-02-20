package com.javamain.schema;

import com.javamain.schema.column.Column;
import com.javamain.schema.typeutils.SQLParseUtils;
import com.javamain.schema.typeutils.SchemaUtils;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import org.apache.avro.Schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResolverSchema2FlinkDataType03 {
    public static void main(String[] args) throws RestClientException, IOException {

        String topic = "avro_tp02";
        String columnType = "value";
        CachedSchemaRegistryClient registryClient =
                new CachedSchemaRegistryClient(
                        Arrays.asList("http://localhost:7081"), 10);
//        String topic = "v2_dptask_1339.oracle.SYS.src_alltype_0_231229140540414.34243";
//        String columnType = "value";
//        CachedSchemaRegistryClient registryClient =
//                new CachedSchemaRegistryClient(
//                        Arrays.asList("http://81.70.46.229:8081"), 10);

        String subject = String.format("%s-%s", topic, columnType);
        SchemaMetadata metadata;
        metadata = registryClient.getLatestSchemaMetadata(subject);
        Schema schema = new Schema.Parser().parse(metadata.getSchema());

        List<Column> columns = new ArrayList<>();
        if (schema.getType().equals(Schema.Type.RECORD)) {
            for (Schema.Field field : schema.getFields()) {
                columns.add(SchemaUtils.convertToSchemaField(field));
            }
        }
        SQLParseUtils.toCreateSQLString(columns);
        System.out.println("aaaa");
    }
}
