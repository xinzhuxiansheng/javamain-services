package com.javamain.datahub;

import com.aliyun.datahub.client.DatahubClient;
import com.aliyun.datahub.client.DatahubClientBuilder;
import com.aliyun.datahub.client.auth.AliyunAccount;
import com.aliyun.datahub.client.common.DatahubConfig;
import com.aliyun.datahub.client.model.*;

public class TestMain {

    public static void main(String[] args) {
        String DATAHUB_ENDPOINT = "https://dh-cn-hangzhou.xxx.com";
        AliyunAccount account = new AliyunAccount("xxx", "xxx");
        DatahubClient client = DatahubClientBuilder.newBuilder()
                .setDatahubConfig(
                        new DatahubConfig(DATAHUB_ENDPOINT, account)
                ).build();


        getRecordData(client, "xxx", "xx", "0");

    }

    public static void getRecordData(DatahubClient client, String PROJECT_NAME, String TOPIC_NAME, String SHARD_ID) {

        RecordSchema schema = new RecordSchema();
        schema.addField(new Field("id", FieldType.BIGINT, false));
        schema.addField(new Field("name", FieldType.STRING, true));
        schema.addField(new Field("address", FieldType.STRING, true));
        schema.addField(new Field("ext_field01", FieldType.STRING, true));
        schema.addField(new Field("before_ext_field01", FieldType.STRING, true));
        schema.addField(new Field("dp_c_stream_position", FieldType.STRING, true));
        schema.addField(new Field("test_scn", FieldType.STRING, true));

        // get records
        GetCursorResult getCursorResult = client.getCursor(PROJECT_NAME, TOPIC_NAME, SHARD_ID, CursorType.SEQUENCE, 0);
        GetRecordsResult getRecordsResult = client.getRecords(PROJECT_NAME, TOPIC_NAME, SHARD_ID, schema, getCursorResult.getCursor(), 10000);
        for (RecordEntry entry : getRecordsResult.getRecords()) {
            TupleRecordData data = (TupleRecordData) entry.getRecordData();
            System.out.println(
                    "id:" + data.getField("id") +
                            ", name:" + data.getField("name") +
                            ", address:" + data.getField("address") +
                            ", ext_field01:" + data.getField("ext_field01") +
                            ", before_ext_field01:" + data.getField("before_ext_field01") +
                            ", test_scn:" + data.getField("test_scn")

            );
        }


//        //每次最多读取数据量
//        int recordLimit = 1000;
//        String shardId = SHARD_ID;
//        // 获取cursor, 这里获取有效数据中时间最久远的record游标
//        // 注：正常情况下，getCursor只需在初始化时获取一次，然后使用getRecords的nextCursor进行下一次读取
//        String cursor = "";
//        try {
//            cursor = client.getCursor(PROJECT_NAME, TOPIC_NAME, shardId, CursorType.OLDEST).getCursor();
//        } catch (DatahubClientException e) {
//            System.out.println(e.getMessage());
//        }
//        while (true) {
//            try {
//                GetRecordsResult result = client.getRecords(PROJECT_NAME, TOPIC_NAME, shardId, schema, cursor, recordLimit);
//                if (result.getRecordCount() <= 0) {
//                    // 无数据，sleep后读取
//                    Thread.sleep(10000);
//                    continue;
//                }
//                for (RecordEntry entry : result.getRecords()) {
//                    TupleRecordData data = (TupleRecordData) entry.getRecordData();
//                    System.out.println(
//                            "id:" + data.getField("id") +
//                                    ", name:" + data.getField("name") +
//                                    ", address:" + data.getField("address") +
//                                    ", ext_field01:" + data.getField("ext_field01") +
//                                    ", before_ext_field01:" + data.getField("before_ext_field01") +
//                                    ", test_scn:" + data.getField("test_scn") +
//                                    ", dp_c_stream_position:" + data.getField("dp_c_stream_position") +
//                                    ", dp_c_commit_timestamp:" + data.getField("dp_c_commit_timestamp") +
//                                    ", dp_c_commit_time_mill:" + data.getField("dp_c_commit_time_mill"));
//                }
//                // 拿到下一个游标
//                cursor = result.getNextCursor();
//            } catch (InvalidCursorException ex) {
//                // 非法游标或游标已过期，建议重新定位后开始消费
//                cursor = client.getCursor(PROJECT_NAME, TOPIC_NAME, shardId, CursorType.OLDEST).getCursor();
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                ;
//            }
//        }
    }
}
