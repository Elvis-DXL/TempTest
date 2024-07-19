package basejpa.config;

import basejpa.entity.DataAuditEntity;
import com.zx.core.base.context.UserContextHolder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.aspectj.ConfigurableObject;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:45
 */
@Configurable
public class DataAuditEntityListener implements ConfigurableObject {
    @PrePersist
    public void PrePersist(Object obj) {
        DataAuditEntity dataAuditEntity = (DataAuditEntity) obj;
        dataAuditEntity.setCreateTime(LocalDateTime.now());
        dataAuditEntity.setUpdateTime(LocalDateTime.now());
        UserContextHolder.getUser().ifPresent(user -> {
            dataAuditEntity.setCreateUser(user.getId());
            dataAuditEntity.setUpdateUser(user.getId());
        });
    }

    @PreUpdate
    public void preUpdate(Object obj) {
        DataAuditEntity dataAuditEntity = (DataAuditEntity) obj;
        dataAuditEntity.setUpdateTime(LocalDateTime.now());
        UserContextHolder.getUser().ifPresent(user -> {
            dataAuditEntity.setUpdateUser(user.getId());
        });
    }
}