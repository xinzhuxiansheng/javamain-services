package com.yzhou.rpc.base.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    private String modelInterfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
