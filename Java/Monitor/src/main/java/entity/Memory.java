package entity;

import lombok.Data;

@Data
public class Memory {
    /*mb*/
    long size;

    /*可用内存*/
    long avail;

    /*用作缓存的内存*/
    long cache;

    /*free内存*/
    long free;

    /*百分比*/
    double used;

    /*频率*/
    long freq;

    /*内存类型*/
    String type;
}
