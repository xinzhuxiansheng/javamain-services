package com.yzhou.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author yzhou
 * @date 2022/8/9
 */
public class Log4jWrite {
    //private static Logger logger = LoggerFactory.getLogger(Log4jWrite.class);
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        //设置log4j中的环境变量 path
        //System.setProperty("kafka.logs.dir", "/Users/xxx/Code/JAVA/yzhou/javamain-services/javamain-log4j/log4j-exercise");

        String data = "如代码所示，同一个flink任务里有多个处理不同数据流的flatMap，从kafka不同的topic中取数据进行处理，其中stream1有不可预测的异常没有抓住，或者因为环境问题导致整个任务被cancel，然后自动重新启动，重启任务之后都会概率性出现有数条kafka里的数据被重复处理，有木有大神知道怎么解决啊？";
//        int i = 0;
//        while (i < 10) {
//            logger.info("222");
//            System.out.println("111");
//            i++;
//        }
        logger.info("结束");
    }
}
