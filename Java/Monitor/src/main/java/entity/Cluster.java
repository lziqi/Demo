package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cluster {
    String Partition;
    String Avail;
    String TimeLimit;
    String Nodes;
    String State;
    String NodeList;
}

