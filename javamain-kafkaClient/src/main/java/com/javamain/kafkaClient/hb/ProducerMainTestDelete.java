package com.javamain.kafkaClient.hb;

import org.apache.kafka.clients.producer.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

public class ProducerMainTestDelete {

    public static void main(String[] args) throws InterruptedException {

        File KRB5_CONF_FILE =
                Paths.get("/home/ssjs/yzhou/k8s/kafka/krb5.conf").toFile();
        System.setProperty("java.security.krb5.conf", KRB5_CONF_FILE.toString());

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xxx.xxx.xxx.140:9092");
        properties.put("retries", 2);
        properties.put("batch.size", "1048576");
        properties.put("compression.type", "gzip");
        properties.put("linger.ms", "1000");
        properties.put("buffer.memory", "67108864");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("sasl.kerberos.service.name","kafka");
        properties.put("sasl.jaas.config","com.sun.security.auth.module.Krb5LoginModule required doNotPrompt=true useKeyTab=true storeKey=true refreshKrb5Config=true keyTab=\"/home/ssjs/yzhou/k8s/kafka/kafka.keytab\" principal=\"kafka/devcomxxx9a@TDH\";");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("security.protocol","SASL_PLAINTEXT");

        Producer<String,String> producer = new KafkaProducer<String, String>(properties);

        Long i = 0L;
        //while(true){
            String data = "{\"TRANS_DATE\":0,\"PRD_CODE\":\"yzhou_test01_prdcode_001\",\"PRD_NAME\":\"2023年第一期大额产品\",\"MARKET_NO\":\" \",\"PUBLISH_DATE\":0,\"INCOME_DATE\":20231129,\"END_DATE\":20231208,\"MARKET_DATE\":0,\"DELISTED_DATE\":0,\"EXIST_LIMIT\":\"12M\",\"CURR_TYPE\":\"CNY\",\"PUBLISH_PRICE\":100.00000000,\"FACE_VALUE\":100.00000000,\"TOTAL_AMOUNT\":2000000000.00,\"TURNOVER_AMOUNT\":90000000.00,\"INTEREST_TYPE\":\"1\",\"PAY_INTEREST_TYPE\":\"2\",\"PAY_INTEREST_STYLE\":\" \",\"BASIC_RATE_TYPE\":\" \",\"PAY_INTERVAL\":\"3M\",\"FACE_RATE\":6.40000000,\"BASIC_RATE\":0E-8,\"BOOK_MARKET\":\"2\",\"BASIS_ACT\":\"1\",\"END_DATE_ADJUST_METHOD\":\"0\",\"HOLIDAY\":\"CNY\",\"RESET_FREQ\":\"3M\",\"COMPD_METHOD\":\"1\",\"DATES_ADJUST_METHOD\":\"0\",\"BANK_NO\":\"001\",\"OBS_FIRST_DATE\":0,\"INDEX_FIXING_OFFSET\":1,\"OFFSET_TYPE\":\"1\",\"OBS_ADJUST_METHOD\":\" \",\"OBS_ADJUST_HOLIDAY\":\" \",\"INDEX_PRECISION\":0,\"LAST_DATE\":20231130,\"LAST_TIME\":153335,\"APPROVE_STATUS\":\"0\",\"ORDER_DATE\":20231129,\"ORDER_TIME\":0,\"FROZEN_FLAG\":\"1\",\"CHANNELS\":\"1\",\"CLIENT_TYPES\":\"0\",\"CLIENT_GROUPS\":\" \",\"PSUB_UNIT\":0,\"OSUB_UNIT\":100000,\"RESERVE1\":\"heshaosong\",\"RESERVE2\":\" \",\"RESERVE3\":\" \",\"RESERVE4\":\" \",\"RESERVE5\":\" \",\"PFIRST_AMT\":200000.00,\"OFIRST_AMT\":10000000.00,\"PMAX_ACCU_AMT\":0.00,\"OMAX_ACCU_AMT\":2000000000.00,\"DRAW_NUM\":0,\"INCOME_TIME\":152955,\"END_TIME\":152958,\"RESERVE6\":\"1\",\"RESERVE7\":\" \",\"RESERVE8\":\"~~~\",\"RESERVE9\":\"              0\",\"RESERVE10\":\"10.0000\",\"PMAX_AMT\":0.00,\"OMAX_AMT\":0.00,\"TEMPLATE_CODE\":\" \",\"FACE_RATE_TYPE\":\"1\",\"MIN_DRAW_AMT\":0.00,\"TOT_FAIL_TIMES\":0,\"FAIL_TIMES\":0,\"TRANSFER_INTERVAL\":0,\"PAY_INTEREST_DAY\":0,\"PERIOD_TYPE\":\" \",\"PERIOD_INTERVAL\":0,\"PERIOD_TIMES\":0,\"db\":\"jy03db\",\"schema\":\"DECD\",\"table\":\"TBBONDPROPERTY\",\"schema.table\":\"DECD.TBBONDPROPERTY\",\"position\":\"1901553051\",\"current_ts\":\"2023-12-01T15:00:25.136000\",\"op_type\":\"U\"}";
            producer.send(new ProducerRecord<String, String>("CTDS_TBBONDPROPERTY",data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null == recordMetadata){
                        e.printStackTrace();
                    }
                }
            });
            Thread.currentThread().sleep(10000L);
            System.out.println(data);
            i++;
//        }

    }
}
