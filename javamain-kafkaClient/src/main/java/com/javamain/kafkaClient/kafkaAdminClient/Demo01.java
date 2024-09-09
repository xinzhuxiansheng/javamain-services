package com.javamain.kafkaClient.kafkaAdminClient;

import org.apache.kafka.clients.admin.AdminClient;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class Demo01 {

    public static org.apache.kafka.clients.admin.AdminClient initAdminClient(String keytab,String principal) {
        Properties properties = new Properties();
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("sasl.mechanism","GSSAPI");
        properties.put("bootstrap.servers","xxx.xxx.xxx.140:9092,xxx.xxx.xxx.141:9092,xxx.xxx.xxx.142:9092");
        properties.put("group.id","DRC_GET_TP_WEIGHT_GROUP");
        properties.put("sasl.kerberos.service.name","kafka");
        // /home/ssjs/yzhou/k8s/kafka/kafka.keytab
        // hdfs/devcomxxx9a@TDH

        properties.put("sasl.jaas.config",String.format("com.sun.security.auth.module.Krb5LoginModule required doNotPrompt=true useKeyTab=true storeKey=true refreshKrb5Config=true keyTab=\"%s\" principal=\"%s\";",
                keytab,principal));
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("security.protocol","SASL_PLAINTEXT");

        org.apache.kafka.clients.admin.AdminClient client = org.apache.kafka.clients.admin.AdminClient.create(properties);
        return client;
    }

    public static void listTopics(String keytabPath,String principal) throws ExecutionException, InterruptedException {
        AdminClient adminClient = initAdminClient(keytabPath,principal);
        adminClient.listTopics().names().get().stream()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {


        String keytabPath = args[0];
        String principal = args[1];
        String krb5confPath = args[2];


        File KRB5_CONF_FILE =
                Paths.get(krb5confPath).toFile();
        System.setProperty("java.security.krb5.conf", KRB5_CONF_FILE.toString());

        AdminClient adminClient = initAdminClient(keytabPath,principal);
        String clusterId =  adminClient.describeCluster().clusterId().get(15, TimeUnit.MINUTES);
        System.out.println(clusterId);
    }
}
