package com.example.rocketmqdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memory {
    /*mb*/
    long MemorySize;

    /*百分比*/
    double MemoryUsed;
}
