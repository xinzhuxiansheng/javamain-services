package com.javamain.schema;

import com.javamain.schema.typeinfo.SchemaTypeInfo;
import com.javamain.schema.typeutils.Types;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import org.apache.avro.Schema;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResolverSchema2FlinkDataType {
    public static void main(String[] args) throws RestClientException, IOException {

        String topic = "avro_tp01";
        String columnType = "value";
        CachedSchemaRegistryClient registryClient =
                new CachedSchemaRegistryClient(
                        Arrays.asList("http://localhost:7081"), 10);
//        String topic = "v2_dptask_111.pdb3.DP_TEST.XSJ01.8782";
//        String columnType = "value";
//        CachedSchemaRegistryClient registryClient =
//                new CachedSchemaRegistryClient(
//                        Arrays.asList("http://81.70.62.143:8081"), 10);

        String subject = String.format("%s-%s", topic, columnType);
        SchemaMetadata metadata;
        metadata = registryClient.getLatestSchemaMetadata(subject);
        Schema schema = new Schema.Parser().parse(metadata.getSchema());


        // parseSchema(schema, "");
        SchemaTypeInfo typeInfo = convertToTypeInfo(schema);
      //  List<Column> columns = convert2ConfluentAvroColumn(typeInfo);
        //toCreateSQLString(columns);
        System.out.println("aaaa");
    }

    private static SchemaTypeInfo convertToTypeInfo(Schema schema) {
        switch (schema.getType()) {
            case RECORD:
                final List<Schema.Field> fields = schema.getFields();

                final SchemaTypeInfo[] types = new SchemaTypeInfo[fields.size()];
                final String[] names = new String[fields.size()];
                for (int i = 0; i < fields.size(); i++) {
                    final Schema.Field field = fields.get(i);
                    types[i] = convertToTypeInfo(field.schema());
                    names[i] = field.name();
                }
                return Types.Record(names, types);
            case ENUM:
                return Types.Enum();
            case ARRAY:
                // result type might either be ObjectArrayTypeInfo or BasicArrayTypeInfo for Strings
                return Types.OBJECT_ARRAY(convertToTypeInfo(schema.getElementType()));
            case MAP:
                return Types.Map(convertToTypeInfo(schema.getValueType()));
            case UNION:
                Schema actualSchema = null;
                // 针对3个字段 则直接默认返回 String
                if (schema.getTypes().size() > 2) {
                    return Types.String();
                }
                if (schema.getTypes().size() == 2
                        && schema.getTypes().get(0).getType() == Schema.Type.NULL) {
                    actualSchema = schema.getTypes().get(1);
                } else if (schema.getTypes().size() == 2
                        && schema.getTypes().get(1).getType() == Schema.Type.NULL) {
                    actualSchema = schema.getTypes().get(0);
                } else if (schema.getTypes().size() == 1) {
                    actualSchema = schema.getTypes().get(0);
                }
                return convertToTypeInfo(actualSchema);
            case FIXED:
                return Types.Fixed(schema.getLogicalType());
            case STRING:
                return Types.String();
            case BYTES:
                // logical decimal type
                return Types.Bytes(schema.getLogicalType());
            case INT:
                return Types.Int(schema.getLogicalType());
            case LONG:
                return Types.Long(schema.getLogicalType());
            case FLOAT:
                return Types.Float();
            case DOUBLE:
                return Types.Double();
            case BOOLEAN:
                return Types.Boolean();
            case NULL:
                return Types.Void();
        }
        throw new IllegalArgumentException("Unsupported Avro type '" + schema.getType() + "'.");
    }

    /**
     * 处理一层
     */
//    public static List<Column> convert2ConfluentAvroColumn(SchemaTypeInfo schemaTypeInfo) {
//        List<Column> columns = new ArrayList<>();
//        // 仅支持 第一层是 Record 的 Schema
//        if (schemaTypeInfo instanceof RecordTypeInfo) {
//            Map<String, SchemaTypeInfo> fields = ((RecordTypeInfo) schemaTypeInfo).getFields();
//            fields.forEach((key, value) -> {
//                // 在这里处理 key 和 value
//                ConfluentAvroColumn c = ConfluentAvroColumn.builder()
//                        .name(key)
//                        .schemaTypeInfo(value)
//                        .build();
//                columns.add(c);
//            });
//        }
//        return columns;
//    }

//    public static String toCreateSQLString(List<Column> columns) {
//        String start = "CREATE TABLE TABLE_NAME (";
//        System.out.println(start);
//        List<String> columnsLines = new ArrayList<>();
//        columns.forEach(c -> {
//            ConfluentAvroColumn avroColumn = (ConfluentAvroColumn) c;
//            columnsLines.add(SQLConvertUtils.convertaaaa(avroColumn.getName(), avroColumn.getSchemaTypeInfo()) + " COMMENT ''");
//        });
//        System.out.println(columnsLines.stream()
//                .collect(Collectors.joining(",\n")));
//
//        String end = ")";
//        System.out.println(end);
//        return "";
//    }


}
