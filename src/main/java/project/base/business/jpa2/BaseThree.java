package project.base.business.jpa2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import project.base.interfaces.AddBaseInterface;
import project.base.interfaces.DeleteBaseInterface;
import project.base.interfaces.EntityBaseInterface;
import project.base.interfaces.ModifyBaseInterface;

import java.io.Serializable;
import java.util.Collections;

import static project.base.DSUtil.PageReq;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/12 10:01
 */
public abstract class BaseThree<ID extends Serializable, EN extends EntityBaseInterface & DeleteBaseInterface, EN_VO,
        ADD_CMD extends AddBaseInterface<EN>, MOD_CMD extends ModifyBaseInterface<ID, EN>, QUERY_CMD extends PageReq,
        DAO extends JpaRepository<EN, ID> & JpaSpecificationExecutor<EN>> extends BaseOne<ID, EN, EN_VO, QUERY_CMD, DAO> {

    @Transactional
    public EN_VO add(ADD_CMD cmd) {
        EN obj = addToNewEntity(cmd);
        authExist(obj);
        obj.newEntityObjSetPK();
        dao.save(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    @Transactional
    public EN_VO modify(MOD_CMD cmd) {
        EN obj = modifyInOldEntity(cmd, getById(cmd.obtainPK()));
        authExist(obj);
        dao.save(obj);
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