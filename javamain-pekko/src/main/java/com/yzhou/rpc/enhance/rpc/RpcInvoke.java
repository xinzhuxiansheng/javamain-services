package com.yzhou.rpc.enhance.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcInvoke implements Message{

    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

}
