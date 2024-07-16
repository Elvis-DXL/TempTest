package project.base.demopg.business;

import org.springframework.stereotype.Service;
import project.base.base.BaseFive;
import project.base.base.ImEx;
import project.base.demopg.dao.DemoDao;
import project.base.demopg.entity.Demo;
import project.base.demopg.vo.cmd.DemoAddCmd;
import project.base.demopg.vo.cmd.DemoListCmd;
import project.base.demopg.vo.cmd.DemoModifyCmd;
import project.base.demopg.vo.dto.DemoVo;
import project.base.demopg.vo.excel.DemoExcel;

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