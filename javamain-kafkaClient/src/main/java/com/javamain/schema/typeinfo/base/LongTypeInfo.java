package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class LongTypeInfo extends BasicTypeInfo {
    private String typeName = "long";
    private String logicalType;

    public LongTypeInfo(String logicalType) {
        this.logicalType = logicalType;
    }

    public String toFlinkColumnString(String name) {
        return String.format("%s %s", StringUtils.isBlank(name) ? "" : "`" + name + "`", logicalType);
    }
}
