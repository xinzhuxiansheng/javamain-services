package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class BooleanTypeInfo extends BasicTypeInfo {
    private String typeName="boolean";
    public String toFlinkColumnString(String name) {
        return String.format("%s BOOLEAN", StringUtils.isBlank(name) ? "" : String.format("`%s`",name));
    }
}
