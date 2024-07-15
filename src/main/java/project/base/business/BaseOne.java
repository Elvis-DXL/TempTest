package project.base.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import project.base.DSUtil.PageReq;
import project.base.DSUtil.PageResp;

import javax.persistence.EntityManager;
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

import static project.base.DSUtil.trueThrow;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/15 9:06
 */
public abstract class BaseOne<ID extends Serializable, EN, EN_VO, QUERY_CMD extends PageReq, DAO extends BaseDao<EN, ID>> {
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected DAO dao;

    protected void throwBusinessEx(String msg) {
        throw getBusinessEx(msg);
    }

    protected IllegalArgumentException getBusinessEx(String msgStr) {
        return new IllegalArgumentException(msgStr);
    }

    protected EN getById(ID id) {
        //JPA
        EN obj = null;
        trueThrow(null == id, getBusinessEx("传入ID为空"));
        Optional<EN> optional = dao.findById(id);
        trueThrow(!optional.isPresent(), getBusinessEx("传入ID错误"));
        obj = optional.get();

        //MYBATIS-PLUS
//        trueThrow(null == id, getBusinessEx("传入ID为空"));
//        obj = dao.selectById(id);
//        trueThrow(null == obj, getBusinessEx("传入ID错误"));
        return obj;
    }

    public EN_VO query(ID id) {
        return entityToVo(Collections.singletonList(getById(id)), null).get(0);
    }

    public List<EN_VO> list(QUERY_CMD cmd) {
        return entityToVo(listEntity(cmd), cmd);
    }

    public PageResp<EN_VO> page(QUERY_CMD cmd) {
        //JPA
        Page<EN> entityPage = dao.findAll(cmdToSpecification(cmd), PageRequest.of(cmd.getPageNum() - 1, cmd.getPageSize()));
        return new PageResp<EN_VO>()
                .setPageNum(entityPage.getNumber() + 1).setPageSize(entityPage.getSize())
                .setTotalNum((int) entityPage.getTotalElements()).setTotalPage(entityPage.getTotalPages())
                .setDataList(entityToVo(entityPage.getContent(), cmd));

        //MYBATIS-PLUS
//        IPage<EN> entityPage = dao.selectPage(new Page<>(cmd.getPageNum(), cmd.getPageSize()), cmdToWrapper(cmd));
//        return new PageResp<EN_VO>()
//                .setPageNum((int) entityPage.getCurrent()).setPageSize((int) entityPage.getSize())
//                .setTotalNum((int) entityPage.getTotal()).setTotalPage((int) entityPage.getPages())
//                .setDataList(entityToVo(entityPage.getRecords(), cmd));
    }

    protected List<EN> listEntity(QUERY_CMD cmd) {
        //JPA
        return dao.findAll(cmdToSpecification(cmd));
        //MYBATIS-PLUS
//        return dao.selectList(cmdToWrapper(cmd));
    }

    //JPA
    protected Specification<EN> cmdToSpecification(QUERY_CMD cmd) {
        return (root, query, cb) -> cmdToPredicate(cmd, new ArrayList<>(), root, query, cb);
    }

    //MYBATIS-PLUS
//    protected LambdaQueryWrapper<EN> cmdToWrapper(QUERY_CMD cmd) {
//        return cmdInWrapper(Wrappers.<EN>lambdaQuery(), cmd);
//    }

    /*************************************************抽象方法*************************************************/
    protected abstract List<EN_VO> entityToVo(List<EN> dataList, QUERY_CMD cmd);

    //JPA
    protected abstract Predicate cmdToPredicate(QUERY_CMD cmd, List<Predicate> tjList,
                                                Root<EN> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    //MYBATIS-PLUS
//    protected abstract LambdaQueryWrapper<EN> cmdInWrapper(LambdaQueryWrapper<EN> wrapper, QUERY_CMD cmd);
}