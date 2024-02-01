package com.elvis.test.projectbase;

import com.zx.core.base.form.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class BaseBusiness<ID extends Serializable, ENTITY, ENTITY_VO,
        ADD_CMD, MODIFY_CMD, LIST_CMD extends Query, DAO extends BaseDao<ENTITY, ID>> {
    @Autowired
    protected DAO dao;

    protected ENTITY_VO save(ADD_CMD cmd) {
        //由添加命令构建实体对象
        ENTITY entity = this.addToEntity(cmd);
        //校验实体对象是否重复
        this.authExist(entity);
        //调用数据库操作DAO
//        dao.save(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO remove(ID id) {
        //调用数据库操作DAO
//        Optional<ENTITY> optional = dao.findById(id);
//        if (!optional.isPresent()) {
//            throw new BizException("传入ID错误");
//        }
//        ENTITY entity = optional.get();
        ENTITY entity = null;
        //执行自定义的删除操作，打标识逻辑删除或者物理删除
        entity = this.dealRemove(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO modify(MODIFY_CMD cmd) {
//        Optional<ENTITY> optional = dao.findById(this.getModifyCmdId(cmd));
//        if (!optional.isPresent()) {
//            throw new BizException("传入ID错误");
//        }
//        ENTITY oldEntity = optional.get();
        ENTITY oldEntity = null;
        ENTITY entity = this.modifyToOldEntity(cmd, oldEntity);
        this.authExist(entity);
        //调用数据库操作DAO
//        dao.save(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO query(ID id) {
//        Optional<ENTITY> optional = dao.findById(id);
//        if (!optional.isPresent()) {
//            throw new BizException("传入ID错误");
//        }
//        ENTITY entity = optional.get();
        ENTITY entity = null;
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

//    protected List<ENTITY_VO> list(LIST_CMD cmd) {
//
//        List<ENTITY> entityList = null;
//        return this.entityToVo(entityList);
//    }
//
//    protected Object page(LIST_CMD cmd) {
//
//        return null;
//    }

    /*************************************************抽象方法*************************************************/
    protected abstract ENTITY addToEntity(ADD_CMD cmd);

    protected abstract ENTITY modifyToOldEntity(MODIFY_CMD cmd, ENTITY oldEntity);

    protected abstract ID getModifyCmdId(MODIFY_CMD cmd);

    protected abstract void authExist(ENTITY entity);

    protected abstract ENTITY dealRemove(ENTITY entity);

    protected abstract List<ENTITY_VO> entityToVo(List<ENTITY> dataList);

//    protected abstract Specification<ENTITY> cmdToSpecification(LIST_CMD cmd);
}
