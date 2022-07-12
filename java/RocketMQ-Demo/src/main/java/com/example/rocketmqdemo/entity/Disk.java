package com.example.rocketmqdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disk {
    /*mb*/
    long DiskSize;

    /*百分比*/
    double DiskUsed;
}
