package com.javamain.java.nio.noheapdb;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class NoHeapDBTest {
    private static final Logger logger = LoggerFactory.getLogger(NoHeapDBTest.class);
    private NoHeapDB database = null;
    private String tableName = "user_info";

    @Before
    public void initNoHeapDB() throws Exception {

        //获取项目resources绝对路径
        Path resourcesPath = Paths.get("src","test","resources");
        logger.info("databases path: {} . ",resourcesPath.toAbsolutePath());
        //构建NoHeapDB
        database = new NoHeapDB(resourcesPath.toAbsolutePath().toString());
        //构建数据库
        // In Memory
        database.createStore(tableName, DataStore.Storage.PERSISTED, 4);
    }

    @Test
    public void insertDataWithNoHeapDB() throws InterruptedException {
        logger.info("写入测试");
        User user01 = new User();
        //String id = UUID.randomUUID().toString();
        String id = "61923144445555";
        logger.info("用户身份id: {} .",id);
        user01.setId(id);
        user01.setName("阿洋");
        user01.setAddress("中国-北京");
        database.putString(tableName,id, JSONObject.toJSONString(user01));

        logger.info("用户信息: {} 。",database.getStore(tableName).getString(id));
        logger.info("用户总数: {} 。",database.getStore(tableName).getRecordCount());

        Thread.sleep(3000);
    }

    @Test
    public void queryUserInfoWithNoHeapDB() throws InterruptedException {
        logger.info("查询测试");
        String userId = "61923144445555";  // id是根据 insertDataWithNoHeapDB()方法生成，记录下来的

        logger.info("用户信息: {} 。",database.getStore(tableName).getString(userId));

        Thread.sleep(3000);
    }

    /**
     * 别忘记 insertDataWithNoHeapDB(),初始化用户数据
     * @throws InterruptedException
     */
    @Test
    public void deleteUserInfoWithNoHeapDB() throws InterruptedException {
        logger.info("删除测试");
        User user01 = new User();
        String userId = "61923144445555";  // id是根据 insertDataWithNoHeapDB()方法生成，记录下来的

        logger.info("用户信息: {} 。",database.getStore(tableName).getString(userId));

        logger.info("执行删除后");
        database.getStore(tableName).remove(userId);
        logger.info("用户信息: {} 。",database.getStore(tableName).getString(userId));

        Thread.sleep(3000);
    }


}
