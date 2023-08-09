package com.elvis.test.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : Elvis
 * @since : 2022/8/6 15:45
 */
@Data
public class ExcelPojo implements Serializable {
    @ExcelProperty(value = {"姓名"}, index = 0)
    private String name;

    @ExcelProperty(value = {"信息", "年龄"}, index = 1)
    private Integer age;

    @ExcelProperty(value = {"信息", "扩展","性别"}, index = 2)
    private String sex;

    @ExcelProperty(value = {"信息", "扩展","电话"}, index = 3)
    private String tel;
}
