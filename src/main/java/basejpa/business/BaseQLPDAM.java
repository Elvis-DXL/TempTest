package basejpa.business;

import basejpa.dao.BaseDao;
import basejpa.interfaces.AddBase;
import basejpa.interfaces.DeleteBase;
import basejpa.interfaces.EntityBase;
import basejpa.interfaces.ModifyBase;
import com.zx.core.base.form.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Query-List-Page-Delete-Add-Modify
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:44
 */
public abstract class BaseQLPDAM<ID, EN extends EntityBase & DeleteBase, EN_VO,
        ADD_CMD extends AddBase<EN>, MOD_CMD extends ModifyBase<ID, EN>, QUERY_CMD extends Query,
        DAO extends BaseDao<EN, ID>> extends BaseQLPD<ID, EN, EN_VO, QUERY_CMD, DAO> {

    @Transactional
    public EN_VO add(ADD_CMD cmd) {
        EN obj = addToNewEntity(cmd);
        authExist(obj);
        obj.newEntityObjSetPrimaryKey();
        dao.save(obj);
        afterAdd(obj, cmd);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    @Transactional
    public EN_VO modify(MOD_CMD cmd) {
        EN obj = modifyInOldEntity(cmd, getById(cmd.obtainPrimaryKey()));
        authExist(obj);
        dao.save(obj);
        afterModify(obj, cmd);
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

    protected void afterAdd(EN obj, ADD_CMD cmd) {
    }

    protected void afterModify(EN obj, MOD_CMD cmd) {
    }
}