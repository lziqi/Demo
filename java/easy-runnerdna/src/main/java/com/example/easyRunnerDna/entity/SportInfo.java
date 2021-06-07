package com.example.easyRunnerDna.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SportInfo {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Lob
    private String accelerate;/*加速度*/

    @Lob
    private String magnetic;/*磁场*/

    @Lob
    private String direction;/*方向*/

    @Lob
    private String gyroscope;/*陀螺仪*/

    @Lob
    private String gravity;/*重力*/

    @Lob
    private String linear_accelerate;/*线性*/

    String info;
}
