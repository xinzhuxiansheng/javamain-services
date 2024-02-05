package com.javamain.schema.typeutils;

import com.javamain.schema.typeinfo.*;
import com.javamain.schema.typeinfo.base.*;
import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes;

public class Types {

    public static SchemaTypeInfo Enum() {
        return new EnumTypeInfo();
    }

    public static SchemaTypeInfo Record(String[] fieldNames, SchemaTypeInfo[] types) {
        return new RecordTypeInfo();
    }

    public static SchemaTypeInfo OBJECT_ARRAY(SchemaTypeInfo typeInformation) {
        return new ArrayTypeInfo(typeInformation);
    }

    public static SchemaTypeInfo Map(SchemaTypeInfo typeInformation) {

        return new MapTypeInfo(typeInformation);
    }

    public static SchemaTypeInfo Long(LogicalType logicalType) {
        return new LongTypeInfo(handlerLongFlinkTypeByLogicalType(logicalType));
    }

    private static String handlerLongFlinkTypeByLogicalType(LogicalType logicalType) {
        if (logicalType == LogicalTypes.timestampMillis()
                || logicalType == LogicalTypes.timestampMicros()) {
            return "TIMESTAMP";
        } else if (logicalType == LogicalTypes.timeMicros()) {
            return "TIME";
        } else {
            return "BIGINT";
        }
    }

    public static SchemaTypeInfo Int(LogicalType logicalType) {
        return new IntTypeInfo(logicalType);
    }

    public static SchemaTypeInfo Bytes(LogicalType logicalType) {
        return new BytesTypeInfo(handlerBytesFlinkTypeByLogicalType(logicalType));
    }

    private static String handlerBytesFlinkTypeByLogicalType(LogicalType logicalType) {
        if (logicalType instanceof LogicalTypes.Decimal) {
            return "DECIMAL";
        } else {
            return "STRING";
        }
    }

    public static SchemaTypeInfo Fixed(LogicalType logicalType) {
        return new FixedTypeInfo(handlerFixedFlinkTypeByLogicalType(logicalType));
    }

    private static String handlerFixedFlinkTypeByLogicalType(LogicalType logicalType) {
        if (logicalType instanceof LogicalTypes.Decimal) {
            return "DECIMAL";
        } else {
            return "STRING";
        }
    }

    public static StringTypeInfo String() {
        return new StringTypeInfo();
    }

    public static FloatTypeInfo Float() {
        return new FloatTypeInfo();
    }

    public static DoubleTypeInfo Double() {
        return new DoubleTypeInfo();
    }

    public static BooleanTypeInfo Boolean() {
        return new BooleanTypeInfo();
    }

    public static VoidTypeInfo Void() {
        return new VoidTypeInfo();
    }

    public static UnionTypeInfo Union(SchemaTypeInfo typeInformation) {
        return new UnionTypeInfo(typeInformation);
    }

}
