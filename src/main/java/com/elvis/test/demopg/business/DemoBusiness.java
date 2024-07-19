package com.elvis.test.demopg.business;

import org.springframework.stereotype.Service;
import com.elvis.test.base.BaseFive;
import com.elvis.test.base.ImEx;
import com.elvis.test.demopg.dao.DemoDao;
import com.elvis.test.demopg.entity.Demo;
import com.elvis.test.demopg.vo.cmd.DemoAddCmd;
import com.elvis.test.demopg.vo.cmd.DemoListCmd;
import com.elvis.test.demopg.vo.cmd.DemoModifyCmd;
import com.elvis.test.demopg.vo.dto.DemoVo;
import com.elvis.test.demopg.vo.excel.DemoExcel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:58
 */
@Service
public class DemoBusiness
        extends BaseFive<Long, Demo, DemoVo, DemoAddCmd, DemoModifyCmd, DemoExcel, DemoListCmd, DemoDao> {
    @Override
    protected ImEx imEx() {
        return ImEx.getInstance().clazz(DemoExcel.class).fileName("").sheetName("");
    }

    @Override
    protected List<DemoExcel> entityToExcel(List<Demo> dataList) {
        return null;
    }

    @Override
    protected void excelDataIntoDatabase(List<DemoExcel> dataList) {
    }

    @Override
    protected List<DemoVo> entityToVo(List<Demo> dataList, DemoListCmd cmd) {
        return null;
    }

    @Override
    protected Predicate cmdToPredicate(DemoListCmd cmd, List<Predicate> tjList,
                                       Root<Demo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }
}