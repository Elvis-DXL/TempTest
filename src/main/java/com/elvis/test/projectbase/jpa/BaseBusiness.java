package com.elvis.test.projectbase.jpa;

import com.zx.core.base.api.Page;
import com.zx.core.base.form.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * JPA基础业务父类
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/2/4 12:51
 */
public abstract class BaseBusiness<ID extends Serializable, ENTITY, ENTITY_VO, ADD_CMD, MODIFY_CMD,
        LIST_CMD extends Query, DAO extends JpaRepository<ENTITY, ID> & JpaSpecificationExecutor<ENTITY>> {
    @Autowired
    protected DAO dao;

    protected ENTITY getById(ID id) {
        if (null == id) {
            this.throwBusinessException("传入ID为空");
        }
        Optional<ENTITY> optional = dao.findById(id);
        if (!optional.isPresent()) {
            this.throwBusinessException("传入ID错误");
        }
        return optional.get();
    }

    protected ENTITY_VO save(ADD_CMD cmd) {
        ENTITY entity = this.addToEntity(cmd);
        this.authExist(entity);
        entity = this.newObjSetId(entity);
        dao.save(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO remove(ID id) {
        ENTITY entity = this.getById(id);
        entity = this.dealRemove(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO modify(MODIFY_CMD cmd) {
        ENTITY entity = this.modifyToOldEntity(cmd, this.getById(this.getModifyCmdId(cmd)));
        this.authExist(entity);
        dao.save(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO query(ID id) {
        return this.entityToVo(Collections.singletonList(this.getById(id))).get(0);
    }

    protected List<ENTITY_VO> list(LIST_CMD cmd) {
        return this.entityToVo(dao.findAll(this.cmdToSpecification(cmd)));
    }

    protected Page<ENTITY_VO> page(LIST_CMD cmd) {
        org.springframework.data.domain.Page<ENTITY> page =
                dao.findAll(this.cmdToSpecification(cmd), PageRequest.of(cmd.getCurrent() - 1, cmd.getSize()));
        Page<ENTITY_VO> result = new Page<>();
        result.setPageSize(page.getSize());
        result.setTotal((int) page.getTotalElements());
        result.setTotalPage(page.getTotalPages());
        result.setPageIndex(page.getNumber() + 1);
        result.setList(this.entityToVo(page.getContent()));
        return result;
    }

    protected void throwBusinessException(String msgStr) {
        throw new IllegalArgumentException(msgStr);
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ENTITY newObjSetId(ENTITY entity);

    protected abstract ENTITY addToEntity(ADD_CMD cmd);

    protected abstract ENTITY modifyToOldEntity(MODIFY_CMD cmd, ENTITY oldEntity);

    protected abstract ID getModifyCmdId(MODIFY_CMD cmd);

    protected abstract void authExist(ENTITY entity);

    protected abstract ENTITY dealRemove(ENTITY entity);

    protected abstract List<ENTITY_VO> entityToVo(List<ENTITY> dataList);

    protected abstract Specification<ENTITY> cmdToSpecification(LIST_CMD cmd);
}