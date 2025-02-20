package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes;
import org.apache.commons.lang3.StringUtils;

@Data
public class IntTypeInfo extends BasicTypeInfo {
    private String typeName = "int";
    private LogicalType logicalType;

    public IntTypeInfo(LogicalType logicalType) {
        this.logicalType = logicalType;
    }

    public String toFlinkColumnString(String name) {
        String type = "";
        if (logicalType == LogicalTypes.date()) {
            //return String.format("`%s` DATE", name);
            type = "DATE";
        } else if (logicalType == LogicalTypes.timeMillis()) {
            type = "TIME";
            //return String.format("`%s` TIME", name);
        } else {
            type = "INT";
        }
        return String.format("%s %s", StringUtils.isBlank(name) ? "" : "`" + name + "`", type);
    }
}
