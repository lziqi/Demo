package entity;

import lombok.Data;

@Data
public class CPU {
    String CpuName;

    /*运行时间*/
    long upTime;

    /*进程数*/
    int processCount;

    /*线程数*/
    int threadCount;

    /*当前频率*/
    double currentFreq;

    /*基准频率*/
    double maxFreq;

    /*插槽数*/
    int packageCount;

    /*计算了多线程的核心数*/
    int CpuCores;

    /*百分比*/
    double CpuUsed;

    /*温度*/
    double temperature;
}
