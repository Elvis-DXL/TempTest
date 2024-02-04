package com.elvis.test.projectbase.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zx.core.base.form.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * MYBATIS-PLUS基础业务父类
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/2/4 12:51
 */
public abstract class BaseBusiness<ID extends Serializable, ENTITY, ENTITY_VO,
        ADD_CMD, MODIFY_CMD, LIST_CMD extends Query, DAO extends BaseMapper<ENTITY>> {
    @Autowired
    protected DAO dao;

    protected ENTITY getById(ID id) {
        if (null == id) {
            this.throwBusinessException("传入ID为空");
        }
        ENTITY entity = dao.selectById(id);
        if (null == entity) {
            this.throwBusinessException("传入ID错误");
        }
        return entity;
    }

    protected ENTITY_VO save(ADD_CMD cmd) {
        //由添加命令构建实体对象
        ENTITY entity = this.addToEntity(cmd);
        //校验实体对象是否重复
        this.authExist(entity);
        //调用数据库操作DAO
        dao.insert(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO remove(ID id) {
        ENTITY entity = this.getById(id);
        //执行自定义的删除操作，打标识逻辑删除或者物理删除
        entity = this.dealRemove(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO modify(MODIFY_CMD cmd) {
        ENTITY entity = this.modifyToOldEntity(cmd, this.getById(this.getModifyCmdId(cmd)));
        this.authExist(entity);
        //调用数据库操作DAO
        dao.updateById(entity);
        return this.entityToVo(Collections.singletonList(entity)).get(0);
    }

    protected ENTITY_VO query(ID id) {
        return this.entityToVo(Collections.singletonList(this.getById(id))).get(0);
    }

    protected List<ENTITY_VO> list(LIST_CMD cmd) {
        return this.entityToVo(dao.selectList(this.cmdToWrapper(cmd)));
    }

    protected com.zx.core.base.api.Page<ENTITY_VO> page(LIST_CMD cmd) {
        IPage<ENTITY> iPage = new Page<>(cmd.getCurrent(), cmd.getSize());
        IPage<ENTITY> entityiPage = dao.selectPage(iPage, this.cmdToWrapper(cmd));
        com.zx.core.base.api.Page<ENTITY_VO> result = new com.zx.core.base.api.Page<>();
        result.setPageIndex((int) entityiPage.getCurrent());
        result.setPageSize((int) entityiPage.getSize());
        result.setTotal((int) entityiPage.getTotal());
        result.setTotalPage((int) entityiPage.getPages());
        result.setList(this.entityToVo(entityiPage.getRecords()));
        return result;
    }

    protected LambdaQueryWrapper<ENTITY> cmdToWrapper(LIST_CMD cmd) {
        return this.cmdInWrapper(Wrappers.<ENTITY>lambdaQuery(), cmd);
    }

    protected void throwBusinessException(String msgStr) {
        throw new IllegalArgumentException(msgStr);
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ENTITY addToEntity(ADD_CMD cmd);

    protected abstract ENTITY modifyToOldEntity(MODIFY_CMD cmd, ENTITY oldEntity);

    protected abstract ID getModifyCmdId(MODIFY_CMD cmd);

    protected abstract void authExist(ENTITY entity);

    protected abstract ENTITY dealRemove(ENTITY entity);

    protected abstract List<ENTITY_VO> entityToVo(List<ENTITY> dataList);

    protected abstract LambdaQueryWrapper<ENTITY> cmdInWrapper(LambdaQueryWrapper<ENTITY> wrapper, LIST_CMD cmd);
}