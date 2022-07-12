package com.example.rocketmqdemo.Listener;

import com.example.rocketmqdemo.entity.Cluster;
import com.example.rocketmqdemo.entity.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "test_topic",
            consumerGroup = "consumer_group"
)
public class RocketListner implements RocketMQListener<Cluster> {
    @Override
    public void onMessage(Cluster cluster) {
        log.info("收到MQ消息: {}", cluster);
    }
}
