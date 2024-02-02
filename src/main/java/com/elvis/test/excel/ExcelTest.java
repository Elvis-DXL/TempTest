package com.elvis.test.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.zx.core.tool.utils.JsonUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Elvis
 * @since : 2022/8/6 15:46
 */
public class ExcelTest {

    public static void main(String args[]) throws Exception {
        InputStream input = new FileInputStream(new File("D:/TDDOWN/002.xlsx"));
        List<ExcelPojo> dataList = ExcelRW.reader(input, ExcelPojo.class);
        System.out.println(JsonUtil.toJson(dataList));

        InputStream input2 = new FileInputStream(new File("D:/TDDOWN/003.xls"));
        List<ExcelPojo> dataList2 = ExcelRW.reader(input2, ExcelPojo.class, 0);
        System.out.println(JsonUtil.toJson(dataList2));

        InputStream input3 = new FileInputStream(new File("D:/TDDOWN/004.xls"));
        List<ExcelPojo> dataList3 = ExcelRW.reader(input3, ExcelPojo.class, 0);
        System.out.println(JsonUtil.toJson(dataList3));

        InputStream input4 = new FileInputStream(new File("D:/TDDOWN/005.xlsx"));
        List<ExcelPojo> dataList4 = ExcelRW.reader(input4, ExcelPojo.class);
        System.out.println(JsonUtil.toJson(dataList4));
    }

    public static void test1() throws Exception {
        File file = new File("D:/TDDOWN/001.xlsx");

        InputStream input = new FileInputStream(file);
        List<ExcelPojo> data = new ArrayList<>();
        EasyExcelFactory.read(input, ExcelPojo.class, new AnalysisEventListener<ExcelPojo>() {
            @Override
            public void invoke(ExcelPojo rowDTO, AnalysisContext analysisContext) {
                System.out.println(JSONObject.toJSON(rowDTO));
                data.add(rowDTO);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet().doRead();

        File file2 = new File("D:/TDDOWN/002.xlsx");
        OutputStream output = new FileOutputStream(file2);

        EasyExcelFactory.write(output, ExcelPojo.class).sheet("基础数据").doWrite(data);
    }
}
