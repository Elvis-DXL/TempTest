package com.elvis.test.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Elvis
 * @since : 2022/8/6 15:46
 */
public class ExcelTest {

    public static void main(String args[]) throws Exception {
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
