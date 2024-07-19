package com.elvis.test.base;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/15 9:34
 */
public abstract class BaseThree<ID extends Serializable, EN extends InterfaceOfEntityBase & InterfaceOfDeleteBase, EN_VO,
        ADD_CMD extends InterfaceOfAddBase<EN>, MOD_CMD extends InterfaceOfModifyBase<ID, EN>,
        QUERY_CMD extends DSUtil.PageReq, DAO extends BaseDao<EN, ID>> extends BaseTwo<ID, EN, EN_VO, QUERY_CMD, DAO> {

    @Transactional
    public EN_VO add(ADD_CMD cmd) {
        EN obj = addToNewEntity(cmd);
        authExist(obj);
        obj.newEntityObjSetPK();
        dao.save(obj);
        //MYBATIS-PLUS
//        dao.insert(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    @Transactional
    public EN_VO modify(MOD_CMD cmd) {
        EN obj = modifyInOldEntity(cmd, getById(cmd.obtainPK()));
        authExist(obj);
        dao.save(obj);
        //MYBATIS-PLUS
//        dao.updateById(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    protected EN addToNewEntity(ADD_CMD cmd) {
        return cmd.createNewEntityObj();
    }

    protected EN modifyInOldEntity(MOD_CMD cmd, EN oldObj) {
        return cmd.modifyIntoOldEntityObj(oldObj);
    }

    protected void authExist(EN obj) {
    }
}