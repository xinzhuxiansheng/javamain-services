package com.javamain.akka.example.ex02;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzhou
 * @date 2022/12/12
 */
public class OneActor extends AbstractActor {
    private final static Logger logger = LoggerFactory.getLogger(OneActor.class);

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SomeOne.class, someOne -> {
            JSONObject json = new JSONObject();
            json.put("someOne", someOne);
            logger.info(JSONObject.toJSONString(someOne));

            // 把收到的消息对象，转而发给 TwoActor
            ActorRef actorRef = this.getContext().actorOf(Props.create(TwoActor.class, TwoActor::new));
            someOne.setAge(someOne.getAge() + 1);
            actorRef.tell(someOne, this.getSelf());

        }).build();
    }

    public static void main(String[] args) {
        // 定义Actor管理仓库
        ActorSystem actorSystem = ActorSystem.create("yzhou");
        // 把OneActor放入仓库
        ActorRef actorRef = actorSystem.actorOf(Props.create(OneActor.class, OneActor::new), "one_actor");
        // 向OneActor发送一个消息对象
        SomeOne someOne = new SomeOne(1, "liming", 19);
        while(true){
            actorRef.tell(someOne, actorRef.noSender());
        }
    }
}
