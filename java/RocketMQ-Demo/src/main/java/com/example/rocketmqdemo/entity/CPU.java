package com.example.rocketmqdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPU {
    String CpuName;

    /*计算了多线程的核心数*/
    int CpuCores;

    /*百分比*/
    double CpuUsed;
}
