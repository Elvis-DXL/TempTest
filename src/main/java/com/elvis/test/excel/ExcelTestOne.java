package com.elvis.test.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.elvis.commons.utils.CollUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Elvis
 * @since : 2022/8/6 15:46
 */
public class ExcelTestOne {

    public static void main(String args[]) throws Exception {
        List<MultipleSheetExport> dataList = new ArrayList<>();

        List<ExcelPojoOne> oneList = new ArrayList<>();
        oneList.add(new ExcelPojoOne("张三", 1, "男", "111111", "123213123"));
        oneList.add(new ExcelPojoOne("李四", 12, "男", "131", "231"));
        oneList.add(new ExcelPojoOne("王五", 123, "男", "2313123", "fsdfasf"));
        oneList.add(new ExcelPojoOne("赵六", 1123, "女", "13131", "sdfasdf"));
        oneList.add(new ExcelPojoOne("孙七", 111, "男", "1111123123111", "asdfasf"));
        dataList.add(new MultipleSheetExport<ExcelPojoOne>(oneList, ExcelPojoOne.class, "用户长数据"));

        List<ExcelPojoTwo> twoList = new ArrayList<>();
        twoList.add(new ExcelPojoTwo("张三", 1, "男"));
        twoList.add(new ExcelPojoTwo("李四", 12, "男"));
        twoList.add(new ExcelPojoTwo("王五", 123, "男"));
        twoList.add(new ExcelPojoTwo("赵六", 1123, "女"));
        twoList.add(new ExcelPojoTwo("孙七", 111, "男"));
        dataList.add(new MultipleSheetExport<ExcelPojoTwo>(twoList, ExcelPojoTwo.class, "用户短信息"));

        File file2 = new File("D:/TDDOWN/002.xls");
        OutputStream output = new FileOutputStream(file2);
        multipleSheetExport(dataList, output);
    }

    private static void multipleSheetExport(List<MultipleSheetExport> dataList, OutputStream outStream) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        ExcelWriter excelWriter = EasyExcel.write(outStream).build();
        for (MultipleSheetExport item : dataList) {
            WriteSheet sheet = EasyExcel.writerSheet(item.getSheetName()).head(item.getClazz()).build();
            excelWriter.write(item.getData(), sheet);
        }
        excelWriter.finish();
    }
}
