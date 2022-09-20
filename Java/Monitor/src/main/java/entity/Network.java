package entity;

import lombok.Data;

@Data
public class Network {
    String name;

//    String type;

    double send;/*发送 mb/s*/

    double recv;/*接受 mb/s*/

    String ipv4;

    String ipv6;
}
