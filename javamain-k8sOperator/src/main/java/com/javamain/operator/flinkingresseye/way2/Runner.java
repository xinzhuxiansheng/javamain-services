package com.javamain.operator.flinkingresseye.way2;

import io.javaoperatorsdk.operator.Operator;

public class Runner {
    public static void main(String[] args) {
            // 创建 Operator 实例
            Operator operator = new Operator();
            // 注册 Reconciler
            operator.register(new FlinkIngressReconciler());
            // 启动 Operator
            operator.start();
    }
}
