package com.javamain.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzhou
 * @date 2022/5/11
 */
public class TestDemo {

    public static void main(String[] args) throws Exception {

        Map<String, String> groupKey = new HashMap<>();
        groupKey.put("_token", "xxxx");
        groupKey.put("_step", "60");

        CollectorRegistry registry = new CollectorRegistry();

        Gauge duration = Gauge.build()
                .name("yzhou_test01_name")
                .help("yzhou_test01_help")
                .labelNames("jobclusterid")
                .register(registry);

        String reportHost = "19xxx.xxx.xx";
        String reportPort = "9060";

        PushGateway gateway = new PushGateway(String.format("%s:%s", reportHost, reportPort));

        while (true) {
            duration.labels("yzhou_test").set(1);
            gateway.push(registry, "yzhou_test", groupKey);
            System.out.println("发送一条");
            Thread.sleep(3 * 1000);

        }
    }
}
