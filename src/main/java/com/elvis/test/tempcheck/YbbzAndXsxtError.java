package com.elvis.test.tempcheck;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/21 10:18
 */
@Data
public class YbbzAndXsxtError implements Serializable {
    @ExcelProperty(value = {"药品名称"}, index = 0)
    private String ypmc;

    @ExcelProperty(value = {"结算单号"}, index = 1)
    private String jsdh;

    @ExcelProperty(value = {"刷卡时间"}, index = 2)
    private Date sksj;

    @ExcelProperty(value = {"刷卡金额"}, index = 3)
    private BigDecimal skje;

    @ExcelProperty(value = {"刷卡人姓名"}, index = 4)
    private String skrxm;

    @ExcelProperty(value = {"身份证号"}, index = 5)
    private String sfzh;
}
