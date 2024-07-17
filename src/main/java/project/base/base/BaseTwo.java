package project.base.base;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/15 9:27
 */
public abstract class BaseTwo<ID extends Serializable, EN extends InterfaceOfDeleteBase, EN_VO,
        QUERY_CMD extends DSUtil.PageReq, DAO extends BaseDao<EN, ID>> extends BaseOne<ID, EN, EN_VO, QUERY_CMD, DAO> {

    @Transactional
    public EN_VO delete(ID id) {
        EN obj = getById(id);
        dealDelete(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    protected void dealDelete(EN obj) {
        obj.deleteDealMark();
        dao.save(obj);
        //MYBATIS-PLUS
//        dao.updateById(obj);
    }
}