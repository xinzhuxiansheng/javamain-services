package com.javamain.akka.example.ex02;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzhou
 * @date 2022/12/12
 */
public class TwoActor extends AbstractActor {
    private final static Logger logger = LoggerFactory.getLogger(TwoActor.class);


    @Override
    public Receive createReceive() {
        ReceiveBuilder builder = ReceiveBuilder.create();
        builder.match(SomeOne.class, someOne -> {
            logger.info(JSONObject.toJSONString(someOne));
            Thread.sleep(1000);
            // 把消息再回发给发送者
            someOne.setAge(someOne.getAge() + 1);
            this.getSender().tell(someOne, getSelf());
        });
        return builder.build();
    }
}
