package com.elvis.test.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Elvis
 * @since : 2022/8/6 15:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelPojoOne implements Serializable {
    @ExcelProperty(value = {"姓名"}, index = 0)
    private String name;

    @ExcelProperty(value = {"信息", "年龄"}, index = 1)
    private Integer age;

    @ExcelProperty(value = {"信息", "扩展", "性别"}, index = 2)
    private String sex;

    @ExcelProperty(value = {"信息", "扩展", "电话"}, index = 3)
    private String tel;

    @ExcelProperty(value = {"描述"}, index = 4)
    private String des;
}
