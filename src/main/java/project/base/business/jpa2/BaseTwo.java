package project.base.business.jpa2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import project.base.interfaces.DeleteBaseInterface;

import java.io.Serializable;
import java.util.Collections;

import static project.base.DSUtil.PageReq;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/12 9:55
 */
public abstract class BaseTwo<ID extends Serializable, EN extends DeleteBaseInterface, EN_VO, QUERY_CMD extends PageReq,
        DAO extends JpaRepository<EN, ID> & JpaSpecificationExecutor<EN>> extends BaseOne<ID, EN, EN_VO, QUERY_CMD, DAO> {

    @Transactional
    public EN_VO delete(ID id) {
        EN obj = getById(id);
        dealDelete(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    protected void dealDelete(EN obj) {
        obj.deleteDealMark();
        dao.save(obj);
    }
}