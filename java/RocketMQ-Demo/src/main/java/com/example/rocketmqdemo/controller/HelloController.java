package com.example.rocketmqdemo.controller;

import com.example.rocketmqdemo.entity.Cluster;
import com.example.rocketmqdemo.entity.Memory;
import com.example.rocketmqdemo.entity.MqMessage;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("send")
    public Object send() {
        Cluster cluster = new Cluster();
        cluster.setName("12600KF");
        cluster.setType("computation");
        cluster.setState(0);

        Memory memory = Memory.builder().MemorySize(1600).MemoryUsed(0.51).build();
        cluster.setMemory(memory);

        rocketMQTemplate.convertAndSend("test_topic", cluster);
        return "success";
    }
}
