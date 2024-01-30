package com.elvis.test.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : Elvis
 * @since : 2022/8/6 15:46
 */
public class ExcelTestTwo {

    public static void main(String args[]) throws Exception {
        File file = new File("D:/TDDOWN/001.xlsx");
        InputStream input = new FileInputStream(file);

        List<ExcelPojo> data = new ArrayList<>();
        List<ExcelPojo> data2 = new ArrayList<>();
        EasyExcelFactory.read(input, ExcelPojo.class, new AnalysisEventListener<ExcelPojo>() {
            int maxHeadRow = 3;
            List<List<String>> headListData = new ArrayList<>();
            int index = 0;

            @Override
            public void invoke(ExcelPojo rowDTO, AnalysisContext analysisContext) {
                data.add(rowDTO);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }

            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                index++;
                List<String> head = this.mapToHead(headMap);
                headListData.add(head);
                if (index == maxHeadRow) {
                    this.authHead();
                }
            }

            private void authHead() {
                List<String> objHead = this.getObjHeadList();
                List<String> excelHead = this.getExcelHeadList();
                List<String> distinctExcelHead = excelHead.stream().distinct().collect(Collectors.toList());
                if (excelHead.size() != distinctExcelHead.size()) {
                    throw new IllegalArgumentException("表头错误");
                }
                for (String item : objHead) {
                    if (excelHead.contains(item)) {
                        continue;
                    }
                    throw new IllegalArgumentException("表头错误");
                }
            }

            private List<String> getExcelHeadList() {
                int max = 0;
                for (List<String> item : headListData) {
                    if (item.size() > max) {
                        max = item.size();
                    }
                }
                List<String> result = new ArrayList<>();
                for (int index = 0; index < max; index++) {
                    String str = "";
                    for (int j = 0; j < maxHeadRow; j++) {
                        String s = headListData.get(j).get(index);
                        str = str + (null == s ? "" : s) + "_";
                    }
                    result.add(str.substring(0, str.length() - 1));
                }
                return result;
            }

            private List<String> getObjHeadList() {
                Field[] declaredFields = ExcelPojo.class.getDeclaredFields();
                List<String> result = new ArrayList<>();
                for (Field field : declaredFields) {
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    if (null == excelProperty) {
                        continue;
                    }
                    String[] value = excelProperty.value();
                    String str = "";
                    String upStr = "";
                    for (int index = 0; index < maxHeadRow; index++) {
                        if (index < value.length) {
                            upStr = value[index];
                            str = str + upStr + "_";
                            continue;
                        }
                        str = str + upStr + "_";
                    }
                    result.add(str.substring(0, str.length() - 1));
                }
                return result;
            }

            private List<String> mapToHead(Map<Integer, String> headMap) {
                Set<Map.Entry<Integer, String>> entries = headMap.entrySet();
                List<HeadClass> list = new ArrayList<>();
                for (Map.Entry<Integer, String> item : entries) {
                    HeadClass headClass = new HeadClass();
                    headClass.cell = item.getKey();
                    headClass.name = item.getValue();
                    list.add(headClass);
                }
                return list.stream().map(it -> it.name).collect(Collectors.toList());
            }

            class HeadClass {
                int cell;
                String name;
            }
        }).sheet().doRead();
        System.out.println(data);
        System.out.println(data2);
    }
}
