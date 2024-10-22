package basejpa.entity;

import basejpa.config.DataAuditEntityListener;
import basejpa.interfaces.DeleteBase;
import basejpa.interfaces.EntityBase;
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
public class DataAuditEntity extends BaseEntity implements EntityBase, DeleteBase {
    @Column(name = "create_user", columnDefinition = "bigint(22) COMMENT '创建人'")
    protected Long createUser;
    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    protected LocalDateTime createTime;

    @Column(name = "update_user", columnDefinition = "bigint(22) COMMENT '更新人'")
    protected Long updateUser;
    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    protected LocalDateTime updateTime;

    @Column(name = "_delete", columnDefinition = "int(3) COMMENT '删除状态'")
    protected Boolean delete = false;

    @Override
    public void deleteDealMark() {
        this.setDelete(Boolean.TRUE);
    }

    @Override
    public void newEntityObjSetPrimaryKey() {
    }
}