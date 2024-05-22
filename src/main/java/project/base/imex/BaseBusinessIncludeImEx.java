package project.base.imex;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.web.multipart.MultipartFile;
import project.base.jpa.BaseBusiness;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static project.base.DSUtil.EmptyTool.isEmpty;
import static project.base.DSUtil.IOTool;
import static project.base.DSUtil.PageReq;

/**
 * 慕君Dxl个人程序代码开发业务基类(包含导入、导出、模板下载)，非本人，仅供参考使用，请勿修改
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/17 16:07
 */
public abstract class BaseBusinessIncludeImEx<ID extends Serializable,
        EN extends BaseBusiness.PKSet, EN_VO, ADD_CMD, MOD_CMD extends BaseBusiness.PKGet<ID>, EXCEL,
        QUERY_CMD extends PageReq, DAO extends JpaRepository<EN, ID> & JpaSpecificationExecutor<EN>>
        extends BaseBusiness<ID, EN, EN_VO, ADD_CMD, MOD_CMD, QUERY_CMD, DAO> {

    public void template(HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        if (null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName()) || isEmpty(imEx.getSheetName())) {
            throwBusinessException("模板定义信息异常");
        }
        ExcelRW.writer(new ArrayList<>(), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(), request, response);
    }

    public void dataExport(QUERY_CMD cmd, HttpServletRequest request, HttpServletResponse response) {
        ImEx imEx = imEx();
        if (null == imEx || null == imEx.getClazz() || isEmpty(imEx.getFileName()) || isEmpty(imEx.getSheetName())) {
            throwBusinessException("模板定义信息异常");
        }
        ExcelRW.writer(entityToExcel(listEntity(cmd)), imEx.getClazz(), imEx.getFileName(), imEx.getSheetName(),
                request, response);
    }

    public void dataImport(MultipartFile file) {
        ImEx imEx = imEx();
        if (null == imEx || null == imEx.getClazz()) {
            throwBusinessException("模板定义信息异常");
        }
        if (file.isEmpty()) {
            throwBusinessException("传入文件为空");
        }
        List<EXCEL> excelData = null;
        try {
            excelData = ExcelRW.reader(file.getInputStream(), (Class<EXCEL>) imEx.getClazz());
        } catch (Exception e) {
            throwBusinessException(e.getMessage().contains(ExcelRW.TITLE_ERROR) ? "Excel文件表头错误，请重新下载导入模板"
                    : "Excel文件解析异常");
        }
        if (isEmpty(excelData)) {
            throwBusinessException("解析Excel文件获得到的数据为空");
        }
        excelDataIntoDatabase(excelData);
    }

    public final static class ExcelRW {

        public final static String TITLE_ERROR = "表头错误";

        public static <T> List<T> reader(InputStream iStream, Class<T> clazz) {
            return reader(iStream, clazz, 0);
        }

        public static <T> List<T> reader(InputStream iStream, Class<T> clazz, Integer sheetIndex) {
            //获取定义表头信息
            Field[] fields = clazz.getDeclaredFields();
            List<String> titleList = new ArrayList<>();
            for (Field field : fields) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                if (null == excelProperty) {
                    continue;
                }
                String aim = excelProperty.value()[0];
                if (titleList.contains(aim)) {
                    throw new RuntimeException("表头定义中存在相同字段【" + aim + "】");
                }
                titleList.add(aim);
            }
            List<T> result = new ArrayList<>();
            //读取数据
            EasyExcelFactory.read(iStream, clazz, new AnalysisEventListener<T>() {
                @Override
                public void invoke(T rowDTO, AnalysisContext analysisContext) {
                    result.add(rowDTO);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                }

                @Override
                public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                    //获取文件表头
                    List<String> obsTitle = new ArrayList<>();
                    for (Integer it : headMap.keySet()) {
                        obsTitle.add(headMap.get(it));
                    }
                    //校验表头
                    for (String it : titleList) {
                        if (!obsTitle.contains(it)) {
                            throw new RuntimeException(TITLE_ERROR);
                        }
                    }
                }
            }).sheet(sheetIndex).doRead();
            return result;
        }

        public static void writer(List<?> dataList, Class<?> clazz, String fileName, String sheetName,
                                  HttpServletRequest request, HttpServletResponse response) {
            dealWebExportExcelResponseHeader(fileName + ".xlsx", request, response);
            try {
                writer(dataList, clazz, sheetName, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void writer(Workbook wb, String fileName,
                                  HttpServletRequest request, HttpServletResponse response) {
            if (null == wb) {
                throw new NullPointerException("wb must not be null");
            }
            fileName = wb instanceof HSSFWorkbook ? (fileName + ".xls") : (fileName + ".xlsx");
            dealWebExportExcelResponseHeader(fileName, request, response);
            OutputStream out = null;
            try {
                out = response.getOutputStream();
                wb.write(out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOTool.closeStream(out, wb);
            }
        }

        public static void writer(List<?> dataList, Class<?> clazz, String sheetName, OutputStream outStream) {
            EasyExcelFactory.write(outStream, clazz).sheet(sheetName).doWrite(dataList);
        }

        public static void cellMerge(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        }

        public static void addImage(Workbook wb, Sheet sheet,
                                    int firstRow, int lastRow, int firstCol, int lastCol, String imgStr) {
            if (isEmpty(imgStr)) {
                return;
            }
            BASE64Decoder decoder = new BASE64Decoder();
            sheet.createRow(firstRow);
            cellMerge(sheet, firstRow, lastRow, firstCol, lastCol);
            Drawing drawingPatriarch = sheet.createDrawingPatriarch();
            ClientAnchor anchor = wb instanceof HSSFWorkbook ?
                    new HSSFClientAnchor(0, 0, 0, 0, (short) firstCol, firstRow, (short) (lastCol + 1), lastRow + 1)
                    : new XSSFClientAnchor(0, 0, 0, 0, (short) firstCol, firstRow, (short) (lastCol + 1), lastRow + 1);
            String[] arr = imgStr.split("base64,");
            byte[] buffer = new byte[0];
            try {
                buffer = decoder.decodeBuffer(arr[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            drawingPatriarch.createPicture(anchor, wb.addPicture(buffer, Workbook.PICTURE_TYPE_JPEG));
        }

        private static void dealWebExportExcelResponseHeader(String fileName,
                                                             HttpServletRequest request, HttpServletResponse response) {
            String agent = request.getHeader("USER-AGENT").toLowerCase();
            try {
                if (agent.contains("firefox")) {
                    fileName = "=?UTF-8?B?" + new BASE64Encoder().encode(fileName.getBytes("utf-8")) + "?=";
                    fileName = fileName.replaceAll("\r\n", "");
                } else {
                    fileName = URLEncoder.encode(fileName, "utf-8");
                    fileName = fileName.replace("+", " ");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        }
    }

    public final static class ImEx implements Serializable {
        private Class<?> clazz;
        private String fileName;
        private String sheetName;

        private ImEx() {
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String getFileName() {
            return fileName;
        }

        public String getSheetName() {
            return sheetName;
        }

        public static ImEx getInstance() {
            return new ImEx();
        }

        public ImEx clazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public ImEx fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ImEx sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }
    }

    /*************************************************抽象方法*************************************************/
    protected abstract ImEx imEx();

    protected abstract List<EXCEL> entityToExcel(List<EN> dataList);

    protected abstract void excelDataIntoDatabase(List<EXCEL> dataList);
}