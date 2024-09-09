package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FloatTypeInfo extends BasicTypeInfo {
    private String typeName = "float";

    public String toFlinkColumnString(String name) {
        return String.format("%s FLOAT",
                StringUtils.isBlank(name) ? "" : String.format("`%s`",name));
    }
}
