package com.javamain.temp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ctopic {

    public static void main(String[] args) {

        List<String> list = initList();

        list.forEach(m-> {
            String[] arr = StringUtils.splitByWholeSeparatorPreserveAllTokens(m,",");

            String jsonstr = "{\n" +
                    "  \"apiPassword\": \"aXMCMwbPgAhdxfKG\",\n" +
                    "  \"businessLine\": \"315\",\n" +
                    "  \"businessLineName\": \"大数据\",\n" +
                    "  \"clusterId\": 28,\n" +
                    "  \"cmdbId\": \"3230\",\n" +
                    "  \"cmdbName\": \"bdp-kafka-cloud\",\n" +
                    "  \"isCommercial\": 0,\n" +
                    "  \"keepDays\": 72,\n" +
                    "  \"mrPlace\": \"4\",\n" +
                    "  \"mrType\": \"1\",\n" +
                    "  \"oaAccount\": \"zhouyang0627\",\n" +
                    "  \"partitions\": 10,\n" +
                    "  \"productLine\": \"495\",\n" +
                    "  \"productLineName\": \"实时平台\",\n" +
                    "  \"replicas\": 3,\n" +
                    "  \"topicCName\": \"\",\n" +
                    "  \"topicName\": \"\",\n" +
                    "  \"usageScenario\": \"\",\n" +
                    "  \"importantLevel\":4,\n" +
                    "  \"increaseSum\":\"100G\",\n" +
                    "  \"sourceType\":2\n" +
                    "}";

            JSONObject obj = JSON.parseObject(jsonstr);

            if(StringUtils.isNotBlank(arr[0])){
                obj.put("topicName",arr[0]);
                obj.put("topicCName",arr[0]);
                obj.put("usageScenario",arr[0]);
            }


            if(StringUtils.isNotBlank(arr[1])){
                obj.put("partitions",arr[1]);
            }
            if(StringUtils.isNotBlank(arr[2])){
                obj.put("usageScenario",arr[2]);
                obj.put("topicCName",arr[2]);
            }


            //JSONObject result = HttpUtil.httpPost("http://bdp-kafka.cloud.bdp.autohome.com.cn/ops/topic/autoCreateTopicAndAddACLOneStop",obj);
            //System.out.println(result);
        });




    }

    public static List<String> initList(){
        List<String> list = new ArrayList<>();
        list.add("web_applog_event,40,");
        list.add("web_applog_act,5,");
        return list;
    }
}
