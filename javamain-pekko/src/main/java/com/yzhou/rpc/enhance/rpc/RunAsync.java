package com.yzhou.rpc.enhance.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunAsync implements Message{
    private Runnable runnable;
    private long delayTime;
}
