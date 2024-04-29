package com.elvis.test.query;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/1/29 17:37
 */
@Data
@Entity
@Table(name = "bus_medical_record")
public class DBObj implements Serializable {

    private Integer id;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;

    private Date updateTime;

    @Id
    @Column(name = "check_result", columnDefinition = "varchar(1000) COMMENT '校验结果'")
    private String checkResult;
}
