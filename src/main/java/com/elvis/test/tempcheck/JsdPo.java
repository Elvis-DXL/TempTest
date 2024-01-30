package com.elvis.test.tempcheck;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/21 10:12
 */
@Data
@ApiModel("结算单数据数据PO")
public class JsdPo implements Serializable {
    @ExcelProperty(value = {"结算单据号"}, index = 0)
    private String jsh;
    @ExcelProperty(value = {"患者姓名"}, index = 3)
    private String xm;
    @ExcelProperty(value = {"身份证号"}, index = 4)
    private String sfzh;




    /**************************************************************/
//    @ExcelProperty(value = {"机构编码"}, index = 1)
//    private String jgbm;
//    @ExcelProperty(value = {"机构名称"}, index = 2)
//    private String jgmc;
//    @ExcelProperty(value = {"性别"}, index = 5)
//    private Integer xb;
//    @ExcelProperty(value = {"年龄"}, index = 6)
//    private Integer nl;
//    @ExcelProperty(value = {"险种"}, index = 7)
//    private String xz;
//    @ExcelProperty(value = {"患者所在统筹区域名称"}, index = 8)
//    private String hzsztcqymc;
//    @ExcelProperty(value = {"开始时间"}, index = 9)
//    private Date startTime;
//    @ExcelProperty(value = {"结束时间"}, index = 10)
//    private Date endTime;
//    @ExcelProperty(value = {"结算时间"}, index = 11)
//    private Date jssj;
//    @ExcelProperty(value = {"就诊类别"}, index = 12)
//    private String jzlb;
//    @ExcelProperty(value = {"总费用"}, index = 13)
//    private BigDecimal zfy;
//    @ExcelProperty(value = {"基本统筹基金支付金额"}, index = 14)
//    private BigDecimal jbtcjjzfje;
//    @ExcelProperty(value = {"实际起付线"}, index = 15)
//    private BigDecimal sjqfx;
//    @ExcelProperty(value = {"符合范围"}, index = 16)
//    private BigDecimal fhfw;
//    @ExcelProperty(value = {"个人账户支付金额"}, index = 17)
//    private BigDecimal grzhzfje;
}
