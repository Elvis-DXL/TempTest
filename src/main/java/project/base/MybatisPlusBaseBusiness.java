package project.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 慕君Dxl个人程序代码开发业务MYBATIS-PLUS基类，非本人，仅供参考使用，请勿修改
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/25 14:54
 */
public abstract class MybatisPlusBaseBusiness<ID extends Serializable, ENTITY,
        ENTITY_VO, ADD_CMD, MODIFY_CMD, QUERY_CMD extends DSUtil.PageReq, DAO extends BaseMapper<ENTITY>> {
    @Autowired
    protected DAO dao;

    protected void throwBusinessException(String msgStr) {
        throw new IllegalArgumentException(msgStr);
    }

    protected ENTITY getById(ID id) {
        if (null == id) {
            throwBusinessException("传入ID为空");
        }
        ENTITY entity = dao.selectById(id);
        if (null == entity) {
            throwBusinessException("传入ID错误");
        }
        return entity;
    }

    protected ENTITY_VO add(ADD_CMD cmd) {
        ENTITY entity = addToEntity(cmd);
        authExist(entity);
        entity = newObjSetId(entity);
        dao.insert(entity);
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
        dao.updateById(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    protected ENTITY_VO query(ID id) {
        return entityToVo(Collections.singletonList(getById(id)), null).get(0);
    }

    protected List<ENTITY_VO> list(QUERY_CMD cmd) {
        return entityToVo(dao.selectList(cmdToWrapper(cmd)), cmd);
    }

    protected DSUtil.PageResp<ENTITY_VO> page(QUERY_CMD cmd) {
        IPage<ENTITY> page = dao.selectPage(new Page<>(cmd.getPageNum(), cmd.getPageSize()), cmdToWrapper(cmd));
        DSUtil.PageResp<ENTITY_VO> result = new DSUtil.PageResp<>();
        result.setPageNum((int) page.getCurrent());
        result.setPageSize((int) page.getSize());
        result.setTotalNum((int) page.getTotal());
        result.setTotalPage((int) page.getPages());
        result.setDataList(entityToVo(page.getRecords(), cmd));
        return result;
    }

    protected LambdaQueryWrapper<ENTITY> cmdToWrapper(QUERY_CMD cmd) {
        return cmdInWrapper(Wrappers.<ENTITY>lambdaQuery(), cmd);
    }

    protected void authExist(ENTITY entity) {
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ENTITY newObjSetId(ENTITY entity);

    protected abstract ENTITY addToEntity(ADD_CMD cmd);

    protected abstract ENTITY modifyInOldEntity(MODIFY_CMD cmd, ENTITY oldEntity);

    protected abstract ID getModifyCmdId(MODIFY_CMD cmd);

    protected abstract ENTITY dealDelete(ENTITY entity);

    protected abstract List<ENTITY_VO> entityToVo(List<ENTITY> dataList, QUERY_CMD cmd);

    protected abstract LambdaQueryWrapper<ENTITY> cmdInWrapper(LambdaQueryWrapper<ENTITY> wrapper, QUERY_CMD cmd);
}
