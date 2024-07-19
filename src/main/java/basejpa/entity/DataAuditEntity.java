package basejpa.entity;

import basejpa.config.DataAuditEntityListener;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:34
 */
@Data
@MappedSuperclass
@EntityListeners(DataAuditEntityListener.class)
public class DataAuditEntity extends BaseEntity {
    @Column(name = "create_user", columnDefinition = "bigint(22) COMMENT '创建人'")
    private Long createUser;
    @Column(name = "update_user", columnDefinition = "bigint(22) COMMENT '更新人'")
    private Long updateUser;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    private LocalDateTime createTime;
    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    private LocalDateTime updateTime;
}