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

import static project.base.DSUtil.PageReq;
import static project.base.DSUtil.PageResp;

/**
 * 慕君Dxl个人程序代码开发业务MYBATIS-PLUS基类，非本人，仅供参考使用，请勿修改
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/25 14:54
 */
public abstract class MybatisPlusBaseBusiness<ID extends Serializable, EN,
        EN_VO, ADD_CMD, MOD_CMD, QUERY_CMD extends PageReq, DAO extends BaseMapper<EN>> {
    @Autowired
    protected DAO dao;

    protected void throwBusinessException(String msgStr) {
        throw new IllegalArgumentException(msgStr);
    }

    protected EN getById(ID id) {
        if (null == id) {
            throwBusinessException("传入ID为空");
        }
        EN entity = dao.selectById(id);
        if (null == entity) {
            throwBusinessException("传入ID错误");
        }
        return entity;
    }

    public EN_VO add(ADD_CMD cmd) {
        EN entity = addToEntity(cmd);
        authExist(entity);
        entity = newObjSetId(entity);
        dao.insert(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    public EN_VO delete(ID id) {
        EN entity = getById(id);
        entity = dealDelete(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    public EN_VO modify(MOD_CMD cmd) {
        EN entity = modifyInOldEntity(cmd, getById(getModifyCmdId(cmd)));
        authExist(entity);
        dao.updateById(entity);
        return entityToVo(Collections.singletonList(entity), null).get(0);
    }

    public EN_VO query(ID id) {
        return entityToVo(Collections.singletonList(getById(id)), null).get(0);
    }

    public List<EN_VO> list(QUERY_CMD cmd) {
        return entityToVo(dao.selectList(cmdToWrapper(cmd)), cmd);
    }

    public PageResp<EN_VO> page(QUERY_CMD cmd) {
        IPage<EN> entityPage = dao
                .selectPage(new Page<>(cmd.getPageNum(), cmd.getPageSize()), cmdToWrapper(cmd));
        PageResp<EN_VO> result = new DSUtil.PageResp<>();
        result.setPageNum((int) entityPage.getCurrent());
        result.setPageSize((int) entityPage.getSize());
        result.setTotalNum((int) entityPage.getTotal());
        result.setTotalPage((int) entityPage.getPages());
        result.setDataList(entityToVo(entityPage.getRecords(), cmd));
        return result;
    }

    protected LambdaQueryWrapper<EN> cmdToWrapper(QUERY_CMD cmd) {
        return cmdInWrapper(Wrappers.<EN>lambdaQuery(), cmd);
    }

    protected void authExist(EN entity) {
    }

    /*************************************************抽象方法*************************************************/
    protected abstract EN newObjSetId(EN entity);

    protected abstract EN addToEntity(ADD_CMD cmd);

    protected abstract EN modifyInOldEntity(MOD_CMD cmd, EN oldEntity);

    protected abstract ID getModifyCmdId(MOD_CMD cmd);

    protected abstract EN dealDelete(EN entity);

    protected abstract List<EN_VO> entityToVo(List<EN> dataList, QUERY_CMD cmd);

    protected abstract LambdaQueryWrapper<EN> cmdInWrapper(LambdaQueryWrapper<EN> wrapper, QUERY_CMD cmd);
}
