package basejpa.business;

import basejpa.dao.BaseDao;
import basejpa.handle.DataTool;
import com.zx.core.base.api.Page;
import com.zx.core.base.form.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Query-List-Page
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:43
 */
public abstract class BaseQLP<ID, EN, EN_VO, QUERY_CMD extends Query, DAO extends BaseDao<EN, ID>>
        extends BaseTop<ID, EN, DAO> {

    public EN_VO query(ID id) {
        EN obj = getById(id);
        afterQuery(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    public List<EN_VO> list(QUERY_CMD cmd) {
        return entityToVo(listEntity(cmd), cmd);
    }

    public Page<EN_VO> page(QUERY_CMD cmd) {
        org.springframework.data.domain.Page<EN> entityPage = dao.findAll(cmdToSpecification(cmd),
                PageRequest.of(cmd.getCurrent() - 1, cmd.getSize()));
        Page<EN_VO> result = DataTool.pageConvert(entityPage);
        result.setList(entityToVo(entityPage.getContent(), cmd));
        return result;
    }

    protected List<EN> listEntity(QUERY_CMD cmd) {
        return dao.findAll(cmdToSpecification(cmd));
    }

    protected Specification<EN> cmdToSpecification(QUERY_CMD cmd) {
        return (root, query, cb) -> cmdToPredicate(cmd, new ArrayList<>(), root, query, cb);
    }

    protected void afterQuery(EN obj) {
    }

    /*************************************************抽象方法*************************************************/
    protected abstract List<EN_VO> entityToVo(List<EN> dataList, QUERY_CMD cmd);

    protected abstract Predicate cmdToPredicate(QUERY_CMD cmd, List<Predicate> tjList,
                                                Root<EN> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}