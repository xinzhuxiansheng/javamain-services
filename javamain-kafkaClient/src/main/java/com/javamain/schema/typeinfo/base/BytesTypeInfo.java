package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class BytesTypeInfo extends BasicTypeInfo {
    private String typeName = "bytes";
    private String logicalType;
    public BytesTypeInfo(String logicalType) {
        this.logicalType = logicalType;
    }

    public String toFlinkColumnString(String name) {
//        String type = null;
//        if(logicalType instanceof LogicalTypes.Decimal){
//            type = "DECIMAL";
//        }else{
//            type = "STRING";
//        }
        return String.format("%s %s", StringUtils.isBlank(name) ? "" : String.format("`%s`",name), logicalType);
    }
}
