package com.yzhou.rpc.base.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse implements Serializable {

    private Object result;
    private Throwable ex;
    private int status;
}
