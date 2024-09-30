package test.demopg.business;

import basejpaold.business.BaseQLPDAMIE;
import basejpaold.pojo.ImEx;
import test.demopg.dao.DemoDao;
import test.demopg.entity.Demo;
import test.demopg.vo.cmd.DemoAddCmd;
import test.demopg.vo.cmd.DemoListCmd;
import test.demopg.vo.cmd.DemoModifyCmd;
import test.demopg.vo.dto.DemoVo;
import test.demopg.vo.excel.DemoExcel;
import org.springframework.stereotype.Service;

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
        extends BaseQLPDAMIE<Long, Demo, DemoVo, DemoAddCmd, DemoModifyCmd, DemoExcel, DemoListCmd, DemoDao> {
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