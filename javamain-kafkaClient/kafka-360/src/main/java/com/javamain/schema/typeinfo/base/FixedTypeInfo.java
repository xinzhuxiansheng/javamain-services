package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;

/**
 * Schema Fixed 类型 对应 Flink SQL DECIMAL 类型
 */
@Data
public class FixedTypeInfo extends BasicTypeInfo {
    private String typeName = "fixed";
    private String logicalType;

    public FixedTypeInfo(String logicalType) {
        this.logicalType = logicalType;
    }

    public String toFlinkColumnString(String name) {
        return String.format("`%s` %s", name,logicalType);
    }
}
