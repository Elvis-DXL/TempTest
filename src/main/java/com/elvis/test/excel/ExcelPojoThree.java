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
public class ExcelPojoThree implements Serializable {
    @ExcelProperty(value = {"姓名","","测试"}, index = 0)
    private String name;

    @ExcelProperty(value = {"信息", "年龄","测试"}, index = 1)
    private Integer age;

    @ExcelProperty(value = {"信息", "性别","测试"}, index = 2)
    private String sex;
}
