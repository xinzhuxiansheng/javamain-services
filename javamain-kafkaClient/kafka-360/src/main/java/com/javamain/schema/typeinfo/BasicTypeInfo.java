package com.javamain.schema.typeinfo;

import com.javamain.schema.typeinfo.base.*;

/**
 * Type information for primitive types (int, long, double, byte, ...), String, Date, Void,
 * BigInteger, and BigDecimal.
 */
public abstract class BasicTypeInfo extends SchemaTypeInfo {

    private static final long serialVersionUID = -430955220409131770L;

    public static boolean isBasicTypeInfo(SchemaTypeInfo schemaTypeInfo){
        if(schemaTypeInfo instanceof BooleanTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof BytesTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof DoubleTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof EnumTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof FixedTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof FloatTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof IntTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof LongTypeInfo){
            return true;
        }
        if(schemaTypeInfo instanceof StringTypeInfo){
            return true;
        }
        return schemaTypeInfo instanceof VoidTypeInfo;
    }


//    public static final BasicTypeInfo BOOLEAN_TYPE_INFO =
//            new BooleanTypeInfo("BOOLEAN");
//
//    public static final BasicTypeInfo INT_TYPE_INFO =
//            new BasicTypeInfo(
//                    "int");
//    public static final BasicTypeInfo LONG_TYPE_INFO =
//            new BasicTypeInfo(
//                    "long");
//    public static final BasicTypeInfo FLOAT_TYPE_INFO =
//            new BasicTypeInfo(
//                    "float");
//    public static final BasicTypeInfo DOUBLE_TYPE_INFO =
//            new BasicTypeInfo(
//                    "double");
//
//    public static final BasicTypeInfo BYTES_TYPE_INFO =
//            new BasicTypeInfo(
//                    "bytes");
//    public static final BasicTypeInfo STRING_TYPE_INFO =
//            new BasicTypeInfo(
//                    "string");
//    public static final BasicTypeInfo DECIMAL_TYPE_INFO =
//            new BasicTypeInfo(
//                    "decimal");
//    public static final BasicTypeInfo VOID_TYPE_INFO =
//            new BasicTypeInfo("null");
//
//    // --------------------------------------------------------------------------------------------
//
//    private final String typeName;
//
//    protected BasicTypeInfo(String typeName) {
//        this.typeName = typeName;
//    }
//


//    private static final Set<Class> TYPES =
//            new HashSet<>();
//
//    static {
//        TYPES.add(Boolean.class)
//    }

//    static {
//        TYPES.put(String.class, STRING_TYPE_INFO);
//        TYPES.put(Boolean.class, BOOLEAN_TYPE_INFO);
//        TYPES.put(boolean.class, BOOLEAN_TYPE_INFO);
//        TYPES.put(Integer.class, INT_TYPE_INFO);
//        TYPES.put(int.class, INT_TYPE_INFO);
//        TYPES.put(Long.class, LONG_TYPE_INFO);
//        TYPES.put(long.class, LONG_TYPE_INFO);
//        TYPES.put(Float.class, FLOAT_TYPE_INFO);
//        TYPES.put(float.class, FLOAT_TYPE_INFO);
//        TYPES.put(Double.class, DOUBLE_TYPE_INFO);
//        TYPES.put(double.class, DOUBLE_TYPE_INFO);
//        TYPES.put(Void.class, VOID_TYPE_INFO);
//        TYPES.put(void.class, VOID_TYPE_INFO);
//    }
}
