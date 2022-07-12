package com.example.rocketmqdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    String Partition;
    String Avail;
    String TimeLimit;
    String Nodes;
    String State;
    String NodeList;
}
