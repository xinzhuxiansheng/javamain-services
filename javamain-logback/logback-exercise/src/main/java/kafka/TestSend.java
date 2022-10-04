package kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzhou
 * @date 2022/7/29
 */
public class TestSend {
    private static Logger logger = LoggerFactory.getLogger(TestSend.class);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            Log2KafkaProducer.send("aaaaa");
            logger.info("bbbbbb");
            Thread.sleep(10000);
        }
    }
}
