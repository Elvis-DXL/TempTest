package project.base.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static project.base.DSUtil.PageReq;
import static project.base.DSUtil.PageResp;

/**
 * 慕君Dxl个人程序代码开发业务JPA基类，非本人，仅供参考使用，请勿修改
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/25 11:31
 */
public abstract class BaseBusiness<ID extends Serializable,
        EN extends BaseBusiness.PKSet, EN_VO, ADD_CMD, MOD_CMD extends BaseBusiness.PKGet<ID>,
        QUERY_CMD extends PageReq, DAO extends JpaRepository<EN, ID> & JpaSpecificationExecutor<EN>> {
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected DAO dao;

    protected void throwBusinessException(String msg) {
        throw new IllegalArgumentException(msg);
    }

    protected EN getById(ID id) {
        if (null == id) {
            throwBusinessException("传入ID为空");
        }
        Optional<EN> optional = dao.findById(id);
        if (!optional.isPresent()) {
            throwBusinessException("传入ID错误");
        }
        return optional.get();
    }

    public EN_VO add(ADD_CMD cmd) {
        EN obj = addToNewEntity(cmd);
        authExist(obj);
        obj.newObjSetPK();
        dao.save(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    public EN_VO delete(ID id) {
        EN obj = getById(id);
        dealDelete(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    public EN_VO modify(MOD_CMD cmd) {
        EN obj = modifyInOldEntity(cmd, getById(cmd.getPK()));
        authExist(obj);
        dao.save(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    public EN_VO query(ID id) {
        return entityToVo(Collections.singletonList(getById(id)), null).get(0);
    }

    public List<EN_VO> list(QUERY_CMD cmd) {
        return entityToVo(listEntity(cmd), cmd);
    }

    public PageResp<EN_VO> page(QUERY_CMD cmd) {
        Page<EN> entityPage = dao
                .findAll(cmdToSpecification(cmd), PageRequest.of(cmd.getPageNum() - 1, cmd.getPageSize()));
        PageResp<EN_VO> result = new PageResp<>();
        result.setPageNum(entityPage.getNumber() + 1);
        result.setPageSize(entityPage.getSize());
        result.setTotalNum((int) entityPage.getTotalElements());
        result.setTotalPage(entityPage.getTotalPages());
        result.setDataList(entityToVo(entityPage.getContent(), cmd));
        return result;
    }

    protected List<EN> listEntity(QUERY_CMD cmd) {
        return dao.findAll(cmdToSpecification(cmd));
    }

    protected Specification<EN> cmdToSpecification(QUERY_CMD cmd) {
        return (root, query, cb) -> cmdToPredicate(cmd, new ArrayList<>(), root, query, cb);
    }

    protected void authExist(EN obj) {
    }

    /*************************************************抽象方法*************************************************/
    protected abstract EN addToNewEntity(ADD_CMD cmd);

    protected abstract EN modifyInOldEntity(MOD_CMD cmd, EN oldObj);

    protected abstract void dealDelete(EN obj);

    protected abstract List<EN_VO> entityToVo(List<EN> dataList, QUERY_CMD cmd);

    protected abstract Predicate cmdToPredicate(QUERY_CMD cmd, List<Predicate> tjList,
                                                Root<EN> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    /*************************************************内部接口*************************************************/
    public interface PKGet<ID> {
        ID getPK();
    }

    public interface PKSet {
        void newObjSetPK();
    }
}