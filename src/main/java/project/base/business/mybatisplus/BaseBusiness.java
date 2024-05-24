package project.base.business.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static project.base.DSUtil.*;

/**
 * 慕君Dxl个人程序代码开发业务MYBATIS-PLUS基类，非本人，仅供参考使用，请勿修改
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/25 14:54
 */
public abstract class BaseBusiness<ID extends Serializable, EN extends BaseBusiness.PKSet, EN_VO,
        ADD_CMD, MOD_CMD extends BaseBusiness.PKGet<ID>, QUERY_CMD extends PageReq, DAO extends BaseMapper<EN>> {
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected DAO dao;

    protected void throwBusinessEx(String msg) {
        throw getBusinessEx(msg);
    }

    protected IllegalArgumentException getBusinessEx(String msgStr) {
        return new IllegalArgumentException(msgStr);
    }

    protected EN getById(ID id) {
        trueThrow(null == id, getBusinessEx("传入ID为空"));
        EN obj = dao.selectById(id);
        trueThrow(null == obj, getBusinessEx("传入ID错误"));
        return obj;
    }

    public EN_VO add(ADD_CMD cmd) {
        EN obj = addToNewEntity(cmd);
        authExist(obj);
        obj.newObjSetPK();
        dao.insert(obj);
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
        dao.updateById(obj);
        return entityToVo(Collections.singletonList(obj), null).get(0);
    }

    public EN_VO query(ID id) {
        return entityToVo(Collections.singletonList(getById(id)), null).get(0);
    }

    public List<EN_VO> list(QUERY_CMD cmd) {
        return entityToVo(listEntity(cmd), cmd);
    }

    public PageResp<EN_VO> page(QUERY_CMD cmd) {
        IPage<EN> entityPage = dao.selectPage(new Page<>(cmd.getPageNum(), cmd.getPageSize()), cmdToWrapper(cmd));
        return new PageResp<EN_VO>()
                .setPageNum((int) entityPage.getCurrent()).setPageSize((int) entityPage.getSize())
                .setTotalNum((int) entityPage.getTotal()).setTotalPage((int) entityPage.getPages())
                .setDataList(entityToVo(entityPage.getRecords(), cmd));
    }

    protected List<EN> listEntity(QUERY_CMD cmd) {
        return dao.selectList(cmdToWrapper(cmd));
    }

    protected LambdaQueryWrapper<EN> cmdToWrapper(QUERY_CMD cmd) {
        return cmdInWrapper(Wrappers.<EN>lambdaQuery(), cmd);
    }

    protected void authExist(EN obj) {
    }

    /*************************************************抽象方法*************************************************/
    protected abstract EN addToNewEntity(ADD_CMD cmd);

    protected abstract EN modifyInOldEntity(MOD_CMD cmd, EN oldObj);

    protected abstract void dealDelete(EN obj);

    protected abstract List<EN_VO> entityToVo(List<EN> dataList, QUERY_CMD cmd);

    protected abstract LambdaQueryWrapper<EN> cmdInWrapper(LambdaQueryWrapper<EN> wrapper, QUERY_CMD cmd);

    /*************************************************内部接口*************************************************/
    public interface PKGet<ID> {
        ID getPK();
    }

    public interface PKSet {
        void newObjSetPK();
    }
}