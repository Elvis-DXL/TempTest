package project.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
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
public abstract class JPABaseBusiness<ID extends Serializable, ENTITY, ENTITY_VO, ADD_CMD, MODIFY_CMD,
        QUERY_CMD extends PageReq, DAO extends JpaRepository<ENTITY, ID> & JpaSpecificationExecutor<ENTITY>> {
    @Autowired
    protected DAO dao;

    protected void throwBusinessException(String msgStr) {
        throw new IllegalArgumentException(msgStr);
    }

    protected ENTITY getById(ID id) {
        if (null == id) {
            throwBusinessException("传入ID为空");
        }
        Optional<ENTITY> optional = dao.findById(id);
        if (!optional.isPresent()) {
            throwBusinessException("传入ID错误");
        }
        return optional.get();
    }

    protected ENTITY_VO add(ADD_CMD cmd) {
        ENTITY entity = addToEntity(cmd);
        authExist(entity);
        entity = newObjSetId(entity);
        dao.save(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO delete(ID id) {
        ENTITY entity = getById(id);
        entity = dealDelete(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO modify(MODIFY_CMD cmd) {
        ENTITY entity = modifyInOldEntity(cmd, getById(getModifyCmdId(cmd)));
        authExist(entity);
        dao.save(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO query(ID id) {
        return entityToVo(Collections.singletonList(getById(id)), null).get(0);
    }

    protected List<ENTITY_VO> list(QUERY_CMD cmd) {
        return entityToVo(dao.findAll(cmdToSpecification(cmd)), cmd);
    }

    protected PageResp<ENTITY_VO> page(QUERY_CMD cmd) {
        Page<ENTITY> page = dao.findAll(cmdToSpecification(cmd), PageRequest.of(cmd.getPageNum() - 1, cmd.getPageSize()));
        PageResp<ENTITY_VO> result = new DSUtil.PageResp<>();
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalNum((int) page.getTotalElements());
        result.setTotalPage(page.getTotalPages());
        result.setDataList(entityToVo(page.getContent(), cmd));
        return result;
    }

    protected ENTITY newObjSetId(ENTITY entity) {
        return entity;
    }

    protected void authExist(ENTITY entity) {
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ENTITY addToEntity(ADD_CMD cmd);

    protected abstract ENTITY modifyInOldEntity(MODIFY_CMD cmd, ENTITY oldEntity);

    protected abstract ID getModifyCmdId(MODIFY_CMD cmd);

    protected abstract ENTITY dealDelete(ENTITY entity);

    protected abstract List<ENTITY_VO> entityToVo(List<ENTITY> dataList, QUERY_CMD cmd);

    protected abstract Specification<ENTITY> cmdToSpecification(QUERY_CMD cmd);
}
