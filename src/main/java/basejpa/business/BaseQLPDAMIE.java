package basejpa.business;

import basejpa.dao.BaseDao;
import basejpa.interfaces.AddBase;
import basejpa.interfaces.DeleteBase;
import basejpa.interfaces.EntityBase;
import basejpa.interfaces.ModifyBase;
import basejpa.pojo.ImEx;
import basejpa.util.ExcelRW;
import com.zx.core.base.form.Query;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static basejpa.util.DSUtil.EmptyTool.isEmpty;
import static basejpa.util.DSUtil.trueThrow;

/**
 * Query-List-Page-Delete-Add-Modify-Import-Export
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:44
 */
public abstract class BaseQLPDAMIE<ID, EN extends EntityBase & DeleteBase, EN_VO,
        ADD_CMD extends AddBase<EN>, MOD_CMD extends ModifyBase<ID, EN>, EXCEL, QUERY_CMD extends Query,
        DAO extends BaseDao<EN, ID>> extends BaseQLPDAM<ID, EN, EN_VO, ADD_CMD, MOD_CMD, QUERY_CMD, DAO> {

    public void template(HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName())
                || isEmpty(imEx.getSheetName()), getBusinessEx("模板定义信息异常"));
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
        trueThrow(null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName())
                || isEmpty(imEx.getSheetName()), getBusinessEx("模板定义信息异常"));
        ExcelRW.writer(entityToExcel(listEntity(cmd)), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(),
                request, response);
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ImEx imEx();

    protected abstract List<EXCEL> entityToExcel(List<EN> dataList);

    protected abstract void excelDataIntoDatabase(List<EXCEL> dataList);
}