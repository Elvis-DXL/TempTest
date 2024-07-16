package project.base.base;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/15 9:43
 */
public abstract class BaseFive<ID extends Serializable, EN extends InterfaceOfEntityBase & InterfaceOfDeleteBase, EN_VO,
        ADD_CMD extends InterfaceOfAddBase<EN>, MOD_CMD extends InterfaceOfModifyBase<ID, EN>, EXCEL, QUERY_CMD extends DSUtil.PageReq,
        DAO extends BaseDao<EN, ID>> extends BaseThree<ID, EN, EN_VO, ADD_CMD, MOD_CMD, QUERY_CMD, DAO> {

    public void template(HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        DSUtil.trueThrow(null == imEx || null == imEx.getClazz() || DSUtil.EmptyTool.isEmpty(imEx.getFileName())
                || DSUtil.EmptyTool.isEmpty(imEx.getSheetName()), getBusinessEx("模板定义信息异常"));
        ExcelRW.writer(new ArrayList<>(), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(), request, response);
    }

    public void dataImport(MultipartFile file) {
        ImEx imEx = imEx();
        DSUtil.trueThrow(null == imEx || null == imEx.getClazz(), getBusinessEx("模板定义信息异常"));
        DSUtil.trueThrow(file.isEmpty(), getBusinessEx("传入文件为空"));
        List<EXCEL> excelData = null;
        try {
            excelData = ExcelRW.reader(file.getInputStream(), (Class<EXCEL>) imEx.getClazz());
        } catch (Exception e) {
            e.printStackTrace();
            throwBusinessEx(e.getMessage().contains(ExcelRW.TITLE_ERROR) ? "Excel文件表头错误，请重新下载导入模板"
                    : "Excel文件解析异常");
        }
        if (DSUtil.EmptyTool.isEmpty(excelData)) {
            throwBusinessEx("解析Excel文件获得到的数据为空");
        }
        excelDataIntoDatabase(excelData);
    }

    public void dataExport(QUERY_CMD cmd, HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        DSUtil.trueThrow(null == imEx || null == imEx.getClazz() || DSUtil.EmptyTool.isEmpty(imEx.getFileName())
                || DSUtil.EmptyTool.isEmpty(imEx.getSheetName()), getBusinessEx("模板定义信息异常"));
        ExcelRW.writer(entityToExcel(listEntity(cmd)), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(),
                request, response);
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ImEx imEx();

    protected abstract List<EXCEL> entityToExcel(List<EN> dataList);

    protected abstract void excelDataIntoDatabase(List<EXCEL> dataList);
}