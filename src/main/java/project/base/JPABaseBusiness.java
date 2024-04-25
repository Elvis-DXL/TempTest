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

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/25 11:31
 */
public abstract class JPABaseBusiness<ID extends Serializable, ENTITY, ENTITY_VO, ADD_CMD, MODIFY_CMD,
        QUERY_CMD extends DSUtil.PageReq, DAO extends JpaRepository<ENTITY, ID> & JpaSpecificationExecutor<ENTITY>> {
    @Autowired
    protected DAO dao;

    protected void throwBusinessException(String msgStr) {
        throw new IllegalArgumentException(msgStr);
    }

    protected ENTITY getById(ID id) {
        if (null == id) {
            this.throwBusinessException("传入ID为空");
        }
        Optional<ENTITY> optional = dao.findById(id);
        if (!optional.isPresent()) {
            this.throwBusinessException("传入ID错误");
        }
        return optional.get();
    }

    protected ENTITY_VO add(ADD_CMD cmd) {
        ENTITY entity = this.addToEntity(cmd);
        this.authExist(entity);
        entity = this.newObjSetId(entity);
        dao.save(entity);
        return this.entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO delete(ID id) {
        ENTITY entity = this.getById(id);
        entity = this.dealDelete(entity);
        return this.entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO modify(MODIFY_CMD cmd) {
        ENTITY entity = this.modifyInOldEntity(cmd, this.getById(this.getModifyCmdId(cmd)));
        this.authExist(entity);
        dao.save(entity);
        return this.entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO query(ID id) {
        return this.entityToVo(Collections.singletonList(this.getById(id)), null).get(0);
    }

    protected List<ENTITY_VO> list(QUERY_CMD cmd) {
        return this.entityToVo(dao.findAll(this.cmdToSpecification(cmd)), cmd);
    }

    protected DSUtil.PageResp<ENTITY_VO> page(QUERY_CMD cmd) {
        Page<ENTITY> page = dao
                .findAll(this.cmdToSpecification(cmd), PageRequest.of(cmd.getPageNum() - 1, cmd.getPageSize()));
        DSUtil.PageResp<ENTITY_VO> result = new DSUtil.PageResp<>();
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalNum((int) page.getTotalElements());
        result.setTotalPage(page.getTotalPages());
        result.setDataList(this.entityToVo(page.getContent(), cmd));
        return result;
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ENTITY newObjSetId(ENTITY entity);

    protected abstract ENTITY addToEntity(ADD_CMD cmd);

    protected abstract ENTITY modifyInOldEntity(MODIFY_CMD cmd, ENTITY oldEntity);

    protected abstract ID getModifyCmdId(MODIFY_CMD cmd);

    protected abstract void authExist(ENTITY entity);

    protected abstract ENTITY dealDelete(ENTITY entity);

    protected abstract List<ENTITY_VO> entityToVo(List<ENTITY> dataList, QUERY_CMD cmd);

    protected abstract Specification<ENTITY> cmdToSpecification(QUERY_CMD cmd);
}
