package com.javamain.calcite.udf;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.type.OperandTypes;
import org.apache.calcite.sql.type.ReturnTypes;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlBase64DecodeFunction extends SqlFunction {

    public SqlBase64DecodeFunction() {
        super("base64decode",
              SqlKind.OTHER_FUNCTION,
              ReturnTypes.VARCHAR_NULLABLE,
              null,
              OperandTypes.or(OperandTypes.CHARACTER, OperandTypes.sequence("'base64decode'", OperandTypes.CHARACTER, OperandTypes.NUMERIC)),
              SqlFunctionCategory.STRING);
    }

    @Override
    public SqlCall createCall(@Nullable SqlLiteral functionQualifier, SqlParserPos pos, @Nullable SqlNode... operands) {
        return super.createCall(functionQualifier, pos, operands);
    }
}
