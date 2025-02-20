package com.javamain.schema.typeinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class SchemaTypeInfo implements Serializable {

    private static final long serialVersionUID = -7742311969684489493L;

    public abstract String toFlinkColumnString(String name);
}

