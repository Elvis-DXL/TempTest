package com.elvis.test.base.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/15 9:07
 */
public interface BaseDao<EN, ID> extends JpaRepository<EN, ID>, JpaSpecificationExecutor<EN> {
    //MYBATIS-PLUS
//        BaseMapper<EN> {
}