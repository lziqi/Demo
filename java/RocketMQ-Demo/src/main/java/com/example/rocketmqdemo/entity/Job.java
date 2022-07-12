package com.example.rocketmqdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    String ID;
    String Partition;
    String Name;
    String User;
    String ST;
    String Time;
    String Nodes;
    String NodeList;
}
