package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class StringTypeInfo extends BasicTypeInfo {
    private String typeName="string";

    public String toFlinkColumnString(String name) {
        return String.format("%s STRING", StringUtils.isBlank(name) ? "" : "`" + name + "`");
    }
}
