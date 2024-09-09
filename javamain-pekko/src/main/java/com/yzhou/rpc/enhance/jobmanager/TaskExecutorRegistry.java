package com.yzhou.rpc.enhance.jobmanager;

import com.yzhou.rpc.enhance.taskmanager.TaskExecutorGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskExecutorRegistry {

    private String resourceId;
    private String address;
    private TaskExecutorGateway taskExecutor;
}
