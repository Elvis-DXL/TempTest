package basejpaold.business;

import basejpaold.dao.BaseDao;
import basejpaold.interfaces.DeleteBase;
import basejpaold.pojo.ImEx;
import basejpaold.util.ExcelRW;
import com.zx.core.base.form.Query;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static basejpaold.util.DSUtil.EmptyTool.isEmpty;
import static basejpaold.util.DSUtil.trueThrow;

/**
 * Query-List-Page-Delete-Import-Export
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:44
 */
public abstract class BaseQLPDIE<ID, EN extends DeleteBase, EN_VO, EXCEL,
        QUERY_CMD extends Query, DAO extends BaseDao<EN, ID>> extends BaseQLPD<ID, EN, EN_VO, QUERY_CMD, DAO> {

    public void template(HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName()) || isEmpty(imEx.getSheetName()),
                bizEx("模板定义信息异常"));
        ExcelRW.writer(new ArrayList<>(), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(), request, response);
    }

    public void dataImport(MultipartFile file) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz(), bizEx("模板定义信息异常"));
        trueThrow(file.isEmpty(), bizEx("传入文件为空"));
        List<EXCEL> excelData = null;
        try {
            excelData = ExcelRW.reader(file.getInputStream(), (Class<EXCEL>) imEx.getClazz());
        } catch (Exception e) {
            e.printStackTrace();
            throwBizEx(e.getMessage().contains(ExcelRW.TITLE_ERROR) ? "Excel文件表头错误，请重新下载导入模板"
                    : "Excel文件解析异常");
        }
        if (isEmpty(excelData)) {
            throwBizEx("解析Excel文件获得到的数据为空");
        }
        excelDataIntoDatabase(excelData);
    }

    public void dataExport(QUERY_CMD cmd, HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        trueThrow(null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName()) || isEmpty(imEx.getSheetName()),
                bizEx("模板定义信息异常"));
        ExcelRW.writer(entityToExcel(listEntity(cmd)), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(),
                request, response);
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ImEx imEx();

    protected abstract List<EXCEL> entityToExcel(List<EN> dataList);

    protected abstract void excelDataIntoDatabase(List<EXCEL> dataList);
}