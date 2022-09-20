package entity;

import lombok.Data;

@Data
public class CPU {
    String name;

    /*运行时间*/
    long upTime;

    /*进程数*/
    int processCount;

    /*线程数*/
    int threadCount;

    /*当前频率*/
    double currentFreq;

    /*基准频率*/
    double freq;

    /*插槽数*/
    int packageCount;

    /*物理核心数*/
    int physicalCores;

    /*计算了多线程的核心数*/
    int cores;

    /*占用率*/
    double used;

    /*温度*/
    double temperature;
}
