package com.javamain.kafkaClient.consumer.hebei;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerMain {

    public static void main(String[] args) {
        String gid = args[0];

        File KRB5_CONF_FILE =
                Paths.get("/home/xxxxx/yzhou/k8s/kafka/krb5.conf").toFile();
        System.setProperty("java.security.krb5.conf", KRB5_CONF_FILE.toString());

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xxx.xxx.xxx.140:9092");
        properties.setProperty("group.id", gid);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.put("sasl.kerberos.service.name","kafka");
        properties.put("sasl.jaas.config","com.sun.security.auth.module.Krb5LoginModule required doNotPrompt=true useKeyTab=true storeKey=true refreshKrb5Config=true keyTab=\"/home/ssjs/yzhou/k8s/kafka/kafka.keytab\" principal=\"kafka/devcomxxx9a@TDH\";");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("security.protocol","SASL_PLAINTEXT");


        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("xxxxx"));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
            }
        }


    }

}

