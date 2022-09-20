package entity;

import lombok.Data;

@Data
public class GPU {
    String name;/*gpu名*/

    double used;/*gpu使用率*/

    double temperature;/*温度*/

    String driverVersion;/*驱动器版本*/

    String vbiosVersion;/*vbios版本*/

    long memorySize;/*显存总大小*/

    long memoryUsed;/*已使用显存*/
}
