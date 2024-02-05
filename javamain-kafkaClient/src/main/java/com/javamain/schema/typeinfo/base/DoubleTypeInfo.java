package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class DoubleTypeInfo extends BasicTypeInfo {
    private String typeName = "double";

    public String toFlinkColumnString(String name) {
        return String.format("%s DOUBLE", StringUtils.isBlank(name) ? "" : "`" + name + "`");
    }
}
