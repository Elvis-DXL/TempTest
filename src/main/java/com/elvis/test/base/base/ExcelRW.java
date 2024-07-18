package com.elvis.test.base.base;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/31 15:45
 */
public class ExcelRW {
    public final static String TITLE_ERROR = "表头错误";

    public static <T> List<T> reader(InputStream iStream, Class<T> clazz) {
        return reader(iStream, clazz, 0);
    }

    public static <T> List<T> reader(InputStream iStream, Class<T> clazz, Integer sheetIndex) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> titleList = new ArrayList<>();
        for (Field field : fields) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (null == excelProperty) {
                continue;
            }
            String aim = excelProperty.value()[0];
            DSUtil.trueThrow(titleList.contains(aim), new RuntimeException("表头定义中存在相同字段【" + aim + "】"));
            titleList.add(aim);
        }
        List<T> result = new ArrayList<>();
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
                List<String> obsTitle = new ArrayList<>();
                for (Integer it : headMap.keySet()) {
                    obsTitle.add(headMap.get(it));
                }
                for (String it : titleList) {
                    DSUtil.trueThrow(!obsTitle.contains(it), new RuntimeException(TITLE_ERROR));
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
            throw new RuntimeException(e);
        }
    }

    public static void writer(Workbook wb, String fileName, HttpServletRequest request, HttpServletResponse response) {
        DSUtil.trueThrow(null == wb, new NullPointerException("wb must not be null"));
        fileName = wb instanceof HSSFWorkbook ? (fileName + ".xls") : (fileName + ".xlsx");
        dealWebExportExcelResponseHeader(fileName, request, response);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DSUtil.IOTool.closeStream(out, wb);
        }
    }

    public static void writer(List<?> dataList, Class<?> clazz, String sheetName, OutputStream outStream) {
        EasyExcelFactory.write(outStream, clazz).sheet(sheetName).doWrite(dataList);
    }

    public static void cellMerge(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    public static void addImage(Workbook wb, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, String imgStr) {
        if (DSUtil.EmptyTool.isEmpty(imgStr)) {
            return;
        }
        sheet.createRow(firstRow);
        cellMerge(sheet, firstRow, lastRow, firstCol, lastCol);
        Drawing drawingPatriarch = sheet.createDrawingPatriarch();
        ClientAnchor anchor = wb instanceof HSSFWorkbook ?
                new HSSFClientAnchor(0, 0, 0, 0, (short) firstCol, firstRow, (short) (lastCol + 1), lastRow + 1)
                : new XSSFClientAnchor(0, 0, 0, 0, (short) firstCol, firstRow, (short) (lastCol + 1), lastRow + 1);
        drawingPatriarch.createPicture(anchor, wb.addPicture(Base64.getDecoder().decode(imgStr.split("base64,")[1]),
                Workbook.PICTURE_TYPE_JPEG));
    }

    private static void dealWebExportExcelResponseHeader(String fileName, HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader("USER-AGENT").toLowerCase().contains("firefox")) {
            fileName = "=?UTF-8?B?".concat(Base64.getEncoder().encodeToString(fileName.getBytes(StandardCharsets.UTF_8)))
                    .concat("?=").replaceAll("\r\n", "");
        } else {
            try {
                fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replace("+", " ");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
    }
}
