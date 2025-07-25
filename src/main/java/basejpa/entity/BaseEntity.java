package basejpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 13:15
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
//    @GeneratedValue(generator = "generator")
//    @GenericGenerator(name = "generator", strategy = "native")
    @Column(name = "_id", columnDefinition = "bigint(22) COMMENT '主键'")
    protected Long id;
}