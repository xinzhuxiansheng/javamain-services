package com.javamain.kafkaClient;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class testmain {

    private static Logger logger = LoggerFactory.getLogger(testmain.class);

    public static void main(String[] args) {
        try{
            scsngexecute("jw202004161126", "yzhoutp01",
                    "10.168.100.16:9093");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Properties getConsumeProperties(String groupID, String bootstrap_server) {
        Properties props = new Properties();
        props.put("client.id", "dc");
        props.put("group.id", groupID);
        props.put("bootstrap.servers", bootstrap_server);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public static void scsngexecute(String groupid, String topic, String bootstrap_server) {

        String groupID = groupid;

        Map<Integer, Long> beginOffsetMap = new HashMap<Integer, Long>();
        Map<Integer, Long> endOffsetMap = new HashMap<Integer, Long>();
        Map<Integer, Long> commitOffsetMap = new HashMap<Integer, Long>();

        Properties consumeProps = getConsumeProperties(groupID, bootstrap_server);
        logger.info("consumer properties:" + consumeProps);
        // 查询topic partitions
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumeProps);
        List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
        List<PartitionInfo> partitionsFor = consumer.partitionsFor(topic);
        for (PartitionInfo partitionInfo : partitionsFor) {
            TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
            topicPartitions.add(topicPartition);
        }

        // 查询log size
        Map<TopicPartition,Long> beginOffsets = consumer.beginningOffsets(topicPartitions);
        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(topicPartitions);
        for (TopicPartition partitionInfo : endOffsets.keySet()) {
            beginOffsetMap.put(partitionInfo.partition(),beginOffsets.get(partitionInfo));
            endOffsetMap.put(partitionInfo.partition(), endOffsets.get(partitionInfo));
        }
        for (Integer partitionId : endOffsetMap.keySet()) {
            logger.info(String.format("at %s, topic:%s, partition:%s,  beginOffset:%s, endOffset:%s", System.currentTimeMillis(), topic,
                    partitionId,beginOffsetMap.get(partitionId), endOffsetMap.get(partitionId)));
        }

        // 查询消费offset
        for (TopicPartition topicAndPartition : topicPartitions) {
            OffsetAndMetadata committed = consumer.committed(topicAndPartition);
            if (committed == null) {
                logger.info("partition: " + topicAndPartition.partition() + " , committed 为空");
                return;
            }
            commitOffsetMap.put(topicAndPartition.partition(), committed.offset());
        }

        // 累加lag
        long lagSum = 0l;
        if (endOffsetMap.size() == commitOffsetMap.size()) {
            for (Integer partition : endOffsetMap.keySet()) {
                long endOffSet = endOffsetMap.get(partition);
                long commitOffSet = commitOffsetMap.get(partition);
                long diffOffset = endOffSet - commitOffSet;
                lagSum += diffOffset;
                logger.info("Topic:" + topic + ", groupID:" + groupID + ", partition:" + partition + ", endOffset:"
                        + endOffSet + ", commitOffset:" + commitOffSet + ", diffOffset:" + diffOffset);
            }
            logger.info("Topic:" + topic + ", groupID:" + groupID + ", LAG:" + lagSum);
//            if (lagSum > 1000000) {
//                NoticeService.sendkafkaAlarm("3", "zhouyang0627@autohome.com.cn", "this topic partitions lost");
//            }
        } else {
            logger.info("this topic partitions lost");
            //NoticeService.sendkafkaAlarm("3", "zhouyang0627@autohome.com.cn", "this topic partitions lost");
        }
    }
}
