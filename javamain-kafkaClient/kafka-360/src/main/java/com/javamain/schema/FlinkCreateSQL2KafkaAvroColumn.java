package com.javamain.schema;

import com.javamain.common.util.FileUtils;
import com.javamain.schema.column.*;
import com.javamain.schema.convert.FlinkDataType;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.flink.sql.parser.ddl.SqlCreateTable;
import org.apache.flink.sql.parser.ddl.SqlTableColumn;
import org.apache.flink.sql.parser.ddl.constraint.SqlTableConstraint;
import org.apache.flink.sql.parser.impl.FlinkSqlParserImpl;
import org.apache.flink.sql.parser.type.ExtendedSqlCollectionTypeNameSpec;
import org.apache.flink.sql.parser.type.ExtendedSqlRowTypeNameSpec;
import org.apache.flink.sql.parser.type.SqlMapTypeNameSpec;
import org.apache.flink.sql.parser.validate.FlinkSqlConformance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.javamain.schema.convert.FlinkDataType.*;
import static org.apache.calcite.sql.SqlKind.CREATE_TABLE;

public class FlinkCreateSQL2KafkaAvroColumn {

    private static final SqlParser.Config SQL_CONFIG =
            SqlParser.config()
                    .withParserFactory(FlinkSqlParserImpl.FACTORY)
                    .withQuoting(Quoting.BACK_TICK)
                    .withUnquotedCasing(Casing.UNCHANGED)
                    .withQuotedCasing(Casing.UNCHANGED)
                    .withConformance(FlinkSqlConformance.DEFAULT)
                    .withCaseSensitive(true);

    public static void main(String[] args) {
        String createSql = FileUtils.readFile("/Users/a/Code/Java/javamain-services/javamain-kafkaClient/src/main/resources/sql01.sql");

        List<Column> columns = createSql2KafkaAvroColumns(createSql);
        System.out.println("aaa");
    }

    public static List<Column> createSql2KafkaAvroColumns(String flinkSql) {
        Optional<SqlNode> sqlNode = parseSqlNode(flinkSql);
        if (!sqlNode.isPresent()) {
            throw new RuntimeException();
        }

        if (!sqlNode.get().getKind().equals(CREATE_TABLE)) {
            throw new RuntimeException();
        }

        SqlCreateTable sqlCreateTable = (SqlCreateTable) sqlNode.get();
        // 获取column字段
        SqlNodeList columnList = sqlCreateTable.getColumnList();
        // 获取主键参数
        List<SqlTableConstraint> tableConstraints = sqlCreateTable.getTableConstraints();
        List<String> tableKeys = new ArrayList<>();
        for (SqlTableConstraint tableConstraint : tableConstraints) {
            if (tableConstraint.isPrimaryKey()) {
                SqlNodeList columns = tableConstraint.getColumns();
                for (SqlNode column : columns) {
                    tableKeys.add(column.toString());
                }
            }
        }

        List<SqlTableColumn.SqlRegularColumn> sqlColumns =
                columnList.getList().stream()
                        .map(c -> (SqlTableColumn.SqlRegularColumn) c)
                        .collect(Collectors.toList());

        SqlNodeList partitionKeyList = sqlCreateTable.getPartitionKeyList();

        checkColumns(sqlColumns, tableKeys, partitionKeyList);

        // 最后才是转换
        return convertColumn(sqlColumns, tableKeys);
    }

    private static Optional<SqlNode> parseSqlNode(String flinkSql) {
        SqlNodeList sqlNodeList;
        try {
            SqlParser sqlParser = SqlParser.create(flinkSql, SQL_CONFIG);
            sqlNodeList = sqlParser.parseStmtList();
        } catch (SqlParseException e) {
            throw new RuntimeException();
        }
        List<SqlNode> sqlNodes = sqlNodeList.getList();
        return sqlNodes.stream().findFirst();
    }

    private static void checkColumns(
            List<SqlTableColumn.SqlRegularColumn> sqlColumns,
            List<String> tableKeys,
            SqlNodeList partitionKeyList) {

        // toUpperCase then compare
        List<String> partitions = new ArrayList<>();
        for (SqlNode node : partitionKeyList) {
            partitions.add(node.toString());
        }

        // check
        List<String> sqlColumnNames =
                sqlColumns.stream()
                        .map(sqlColumn -> sqlColumn.getName().toString())
                        .collect(Collectors.toList());

        Map<String, List<String>> columnMap =
                sqlColumnNames.stream().collect(Collectors.groupingBy(c -> c));
        for (Map.Entry<String, List<String>> entry : columnMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new RuntimeException();
            }
        }
        long invalidKey = tableKeys.stream().filter(c -> !sqlColumnNames.contains(c)).count();
        if (invalidKey > 0) {
            throw new RuntimeException();
        }
        long invalidPartitionKey = partitions.stream().filter(c -> !sqlColumnNames.contains(c)).count();
        if (invalidPartitionKey > 0) {
            throw new RuntimeException();
        }
    }

    private static List<Column> convertColumn(
            List<SqlTableColumn.SqlRegularColumn> sqlColumns, List<String> tableKeys) {
        List<Column> columns = new ArrayList<>();
        for (SqlTableColumn.SqlRegularColumn sqlColumn : sqlColumns) {
            if (sqlColumn.getType().getTypeName().toString().equals(ARRAY.getAlias())) {
                columns.add(convertArrayColumn(sqlColumn, tableKeys));
            } else if (sqlColumn.getType().getTypeName().toString().equals(MAP.getAlias())) {
                columns.add(convertMapColumn(sqlColumn, tableKeys));
            } else if (sqlColumn.getType().getTypeName().toString().equals(ROW.getAlias())) {
                columns.add(convertRowColumn(sqlColumn, tableKeys));
            } else { // 只有基础类型 处理精度
                columns.add(convertBasicColumn(sqlColumn, tableKeys));
            }
        }
        return columns;
    }

    private static Column convertBasicColumn(
            SqlTableColumn.SqlRegularColumn sqlColumn, List<String> tableKeys) {
        ConfluentAvroColumn02 column = new ConfluentAvroColumn02();

        initColumn(column, sqlColumn, tableKeys);

        SqlDataTypeSpec type = sqlColumn.getType();
        SqlBasicTypeNameSpec typeNameSpec = (SqlBasicTypeNameSpec) type.getTypeNameSpec();
        FlinkDataType flinkDataType = dataTypeConvert(sqlColumn.getType().toString());
        column.setType(flinkDataType);
        if (hasPrecision(flinkDataType)) {
            column.setPrecision((long) typeNameSpec.getPrecision());
        } else {
            column.setPrecision(0L);
        }
        if (hasScale(flinkDataType)) {
            column.setScale((long) typeNameSpec.getScale());
        } else {
            column.setScale(0L);
        }
        return column;
    }

    private static Column convertMapColumn(
            SqlTableColumn.SqlRegularColumn sqlColumn, List<String> tableKeys) {
        MapColumn column = new MapColumn();

        initColumn(column, sqlColumn, tableKeys);

        FlinkDataType flinkDataType = dataTypeConvert(sqlColumn.getType().getTypeName().toString());
        column.setType(flinkDataType);

        SqlDataTypeSpec type = sqlColumn.getType();
        SqlMapTypeNameSpec typeNameSpec = (SqlMapTypeNameSpec) type.getTypeNameSpec();
        column.setFieldKType(FlinkDataType.valueOf(typeNameSpec.getKeyType().toString()).toString());
        column.setFieldVType(FlinkDataType.valueOf(typeNameSpec.getValType().toString()).toString());
        return column;
    }

    private static Column convertArrayColumn(
            SqlTableColumn.SqlRegularColumn sqlColumn, List<String> tableKeys) {
        ArrayColumn column = new ArrayColumn();

        initColumn(column, sqlColumn, tableKeys);

        FlinkDataType flinkDataType = dataTypeConvert(sqlColumn.getType().getTypeName().toString());
        column.setType(flinkDataType);

        SqlDataTypeSpec type = sqlColumn.getType();
        ExtendedSqlCollectionTypeNameSpec typeNameSpec =
                (ExtendedSqlCollectionTypeNameSpec) type.getTypeNameSpec();
        if (typeNameSpec.getElementTypeName() instanceof ExtendedSqlRowTypeNameSpec) {
            ExtendedSqlRowTypeNameSpec elementTypeName =
                    (ExtendedSqlRowTypeNameSpec) typeNameSpec.getElementTypeName();
            List<RowField> fields = new ArrayList<>();
            for (int i = 0; i < elementTypeName.getFieldTypes().size(); i++) {
                fields.add(
                        RowField.builder()
                                .name(elementTypeName.getFieldNames().get(i).toString())
                                .type(
                                        FlinkDataType.valueOf(elementTypeName.getFieldTypes().get(i).toString())
                                                .getAlias())
                                .comment(getSubFieldComment(elementTypeName.getComments().get(i)))
                                .build());
            }
            column.setNestedType(ROW);
            column.setInternalFields(fields);
        } else if (typeNameSpec.getElementTypeName() instanceof SqlAlienSystemTypeNameSpec) {
            SqlAlienSystemTypeNameSpec elementTypeName =
                    (SqlAlienSystemTypeNameSpec) typeNameSpec.getElementTypeName();
            column.setNestedType(FlinkDataType.valueOf(elementTypeName.getTypeName().toString()));
            column.setInternalFields(
                    FlinkDataType.valueOf(elementTypeName.getTypeName().toString()).getAlias());
        } else if (typeNameSpec.getElementTypeName() instanceof SqlBasicTypeNameSpec) {
            SqlBasicTypeNameSpec elementTypeName =
                    (SqlBasicTypeNameSpec) typeNameSpec.getElementTypeName();
            column.setNestedType(FlinkDataType.valueOf(elementTypeName.getTypeName().toString()));
            column.setInternalFields(
                    FlinkDataType.valueOf(elementTypeName.getTypeName().toString()).getAlias());
        }
        return column;
    }

    private static Column convertRowColumn(
            SqlTableColumn.SqlRegularColumn sqlColumn, List<String> tableKeys) {
        RowColumn column = new RowColumn();

        initColumn(column, sqlColumn, tableKeys);

        FlinkDataType flinkDataType = dataTypeConvert(sqlColumn.getType().getTypeName().toString());
        column.setType(flinkDataType);

        SqlDataTypeSpec type = sqlColumn.getType();
        ExtendedSqlRowTypeNameSpec typeNameSpec = (ExtendedSqlRowTypeNameSpec) type.getTypeNameSpec();
        List<RowField> fields = new ArrayList<>();
        for (int i = 0; i < typeNameSpec.getFieldNames().size(); i++) {
            fields.add(
                    RowField.builder()
                            .name(typeNameSpec.getFieldNames().get(i).toString())
                            .type(
                                    FlinkDataType.valueOf(typeNameSpec.getFieldTypes().get(i).getTypeName().toString()).getAlias())
                            .comment(getSubFieldComment(typeNameSpec.getComments().get(i)))
                            .build());
        }
        column.setInternalFields(fields);
        return column;
    }

    private static void initColumn(
            Column column, SqlTableColumn.SqlRegularColumn sqlColumn, List<String> tableKeys) {
        column.setName(sqlColumn.getName().toString());
        column.setKey(tableKeys.contains(sqlColumn.getName().toString()));
        String comment = "";
        Optional<SqlNode> commentNode = sqlColumn.getComment();
        if (commentNode.isPresent()) {
            SqlCharStringLiteral sqlCharStringLiteral = (SqlCharStringLiteral) commentNode.get();
            comment = sqlCharStringLiteral.getNlsString().getValue();
        }
        column.setComment(comment);
        SqlDataTypeSpec type = sqlColumn.getType();
        column.setNotNull(!type.getNullable());
    }

    public static FlinkDataType dataTypeConvert(String datatype) {
        // ❕以下判断有先后顺序
        if (datatype.equalsIgnoreCase("integer")) {
            return FlinkDataType.INT;
        }
        if (datatype.toLowerCase().startsWith("timestamp")) {
            return FlinkDataType.TIMESTAMP;
        }
        if (datatype.toLowerCase().startsWith("time")) {
            return FlinkDataType.TIME;
        }
        if (datatype.toLowerCase().startsWith("decimal")) {
            return FlinkDataType.DECIMAL;
        }
        return FlinkDataType.valueOf(datatype);
    }

    private static String getSubFieldComment(SqlCharStringLiteral comment) {
        if (comment == null) {
            return null;
        }
        return comment.toString().equals("''") ? null : comment.toString();
    }
}
