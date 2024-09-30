package basejpa.business;

import basejpa.dao.BaseDao;
import basejpa.interfaces.DeleteBase;
import com.zx.core.base.form.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Query-List-Page-Delete
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:43
 */
public abstract class BaseQLPD<ID, EN extends DeleteBase, EN_VO, QUERY_CMD extends Query, DAO extends BaseDao<EN, ID>>
        extends BaseQLP<ID, EN, EN_VO, QUERY_CMD, DAO> {

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