package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*集群中一个节点*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Node {
    private int state;

    private Memory memory;

    private Disk disk;

    private CPU cpu;

    private List<Network> network;

    private List<GPU> gpus;

    /*任务*/
    private List<Job> jobs;

    private List<Cluster> clusters;
}
