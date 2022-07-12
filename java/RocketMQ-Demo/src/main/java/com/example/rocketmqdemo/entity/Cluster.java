package com.example.rocketmqdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.List;

/*集群中一个节点*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cluster implements Serializable {
    String name;

    String type;

    @Value("${whu.node.name}")
    public void setName(String _name) {
        name = _name;
    }

    @Value("${whu.node.type}")
    public void setType(String _type) {
        type = _type;
    }

    int state;

    Memory memory;

    Disk disk;

    CPU cpu;

    List<GPU> gpus;

    /*任务*/
    List<Job> jobs;

    List<Node> nodes;
}
