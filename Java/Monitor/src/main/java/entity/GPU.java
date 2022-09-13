package entity;

import lombok.Data;

@Data
public class GPU {
    String GpuName;/*gpu名*/

    double GpuUsed;/*gpu使用率*/

    String driverVersion;/*驱动器版本*/

    String cudaVersion;/*cuda版本*/

    long memoryUsed;/*已使用显存*/

    long memorySize;/*显存总大小*/
}
