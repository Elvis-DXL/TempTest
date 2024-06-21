package project.base.business.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.web.multipart.MultipartFile;
import project.base.excel.ExcelRW;
import project.base.excel.ImEx;
import project.base.interfaces.DeleteDeal;
import project.base.interfaces.PKGet;
import project.base.interfaces.PKSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static project.base.util.DSUtil.EmptyTool.isEmpty;
import static project.base.util.DSUtil.PageReq;
import static project.base.util.DSUtil.trueThrow;

/**
 * 慕君Dxl个人程序代码开发业务基类(包含导入、导出、模板下载)，非本人，仅供参考使用，请勿修改
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/17 16:07
 */
public abstract class BaseImExBusiness<ID extends Serializable, EN extends PKSet, EN_VO, ADD_CMD, MOD_CMD extends PKGet<ID>,
        EXCEL, QUERY_CMD extends PageReq, DAO extends JpaRepository<EN, ID> & JpaSpecificationExecutor<EN>>
        extends BaseBusiness<ID, EN, EN_VO, ADD_CMD, MOD_CMD, QUERY_CMD, DAO> {

    public void template(HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName()) || isEmpty(imEx.getSheetName()),
                getBusinessEx("模板定义信息异常"));
        ExcelRW.writer(new ArrayList<>(), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(), request, response);
    }

    public void dataImport(MultipartFile file) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz(), getBusinessEx("模板定义信息异常"));
        trueThrow(file.isEmpty(), getBusinessEx("传入文件为空"));
        List<EXCEL> excelData = null;
        try {
            excelData = ExcelRW.reader(file.getInputStream(), (Class<EXCEL>) imEx.getClazz());
        } catch (Exception e) {
            e.printStackTrace();
            throwBusinessEx(e.getMessage().contains(ExcelRW.TITLE_ERROR) ? "Excel文件表头错误，请重新下载导入模板"
                    : "Excel文件解析异常");
        }
        if (isEmpty(excelData)) {
            throwBusinessEx("解析Excel文件获得到的数据为空");
        }
        excelDataIntoDatabase(excelData);
    }

    public void dataExport(QUERY_CMD cmd, HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName()) || isEmpty(imEx.getSheetName()),
                getBusinessEx("模板定义信息异常"));
        ExcelRW.writer(entityToExcel(listEntity(cmd)), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(),
                request, response);
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ImEx imEx();

    protected abstract List<EXCEL> entityToExcel(List<EN> dataList);

    protected abstract void excelDataIntoDatabase(List<EXCEL> dataList);
}