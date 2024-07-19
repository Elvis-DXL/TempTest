package basejpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 11:56
 */
@NoRepositoryBean
public interface BaseDao<EN, ID> extends JpaRepository<EN, ID>, JpaSpecificationExecutor<EN> {
}