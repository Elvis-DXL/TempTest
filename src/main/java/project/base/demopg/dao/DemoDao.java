package project.base.demopg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:53
 */
public interface DemoDao extends JpaRepository<DemoDao, Long>, JpaSpecificationExecutor<DemoDao> {
}
