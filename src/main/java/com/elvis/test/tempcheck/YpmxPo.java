package com.elvis.test.tempcheck;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/21 10:11
 */
@Data
@ApiModel("药品报销明细数据PO")
public class YpmxPo implements Serializable {
    @ExcelProperty(value = {"费用发生时间"}, index = 4)
    private Date fyfssj;
    @ExcelProperty(value = {"医保项目编码"}, index = 1)
    private String ybxmbm;
    @ExcelProperty(value = {"医保项目名称"}, index = 2)
    private String ybxmmc;
    @ExcelProperty(value = {"结算单据号"}, index = 0)
    private String jsh;
    @ExcelProperty(value = {"金额"}, index = 7)
    private BigDecimal je;





    /*******************************************************/
//
//    @ExcelProperty(value = {"医院项目编码"}, index = 1)
//    private String yyxmbm;
//    @ExcelProperty(value = {"医院项目名称"}, index = 2)
//    private String yyxmmc;
//
//
//    @ExcelProperty(value = {"收费项目类别"}, index = 5)
//    private String shxmlb;
//    @ExcelProperty(value = {"单价"}, index = 7)
//    private BigDecimal dj;
//    @ExcelProperty(value = {"数量"}, index = 8)
//    private BigDecimal sl;
//
//    @ExcelProperty(value = {"符合范围金额"}, index = 10)
//    private BigDecimal fhfwje;
}
