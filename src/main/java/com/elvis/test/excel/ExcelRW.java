package com.elvis.test.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelRW {

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
                throw new IllegalArgumentException("表头存在相同字段【" + aim + "】");
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
                        throw new IllegalArgumentException(TITLE_ERROR);
                    }
                }
            }
        }).sheet(sheetIndex).doRead();
        return result;
    }

    public static <T> void writer(List<T> dataList, Class<T> clazz, String fileName, String sheetName,
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
            throw new IllegalArgumentException("wb must not be null");
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
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static <T> void writer(List<T> dataList, Class<T> clazz, String sheetName, OutputStream outStream) {
        EasyExcelFactory.write(outStream, clazz).sheet(sheetName).doWrite(dataList);
    }

    public static void cellMerge(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    public static void addImage(Workbook wb, Sheet sheet,
                                int firstRow, int lastRow, int firstCol, int lastCol, String imgStr) {
        if (StringUtils.isEmpty(imgStr)) {
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
