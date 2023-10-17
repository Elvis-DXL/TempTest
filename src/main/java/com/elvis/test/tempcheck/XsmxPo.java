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
@ApiModel("销售明细数据PO")
public class XsmxPo implements Serializable {
//    @ExcelProperty(value = {"国家医保编码"}, index = 1)
//    private String gjybbm;
    @ExcelProperty(value = {"销售时间"}, index = 1)
    private Date xssj;
    @ExcelProperty(value = {"商品名称"}, index = 0)
    private String spmc;
    /*****************************************************************************/

//    @ExcelProperty(value = {"商品名称"}, index = 2)
//    private String spmc;
//    @ExcelProperty(value = {"规 格"}, index = 3)
//    private String gg;
//    @ExcelProperty(value = {"生产企业"}, index = 4)
//    private String scqy;
//    @ExcelProperty(value = {"单位"}, index = 5)
//    private String dw;
//    @ExcelProperty(value = {"数量"}, index = 6)
//    private BigDecimal sl;
//    @ExcelProperty(value = {"标价"}, index = 7)
//    private BigDecimal bj;
//    @ExcelProperty(value = {"实价"}, index = 8)
//    private BigDecimal sj;
//    @ExcelProperty(value = {"金额"}, index = 9)
//    private BigDecimal je;
//    @ExcelProperty(value = {"个人账户支付"}, index = 10)
//    private BigDecimal grzhzf;
//    @ExcelProperty(value = {"现金支付"}, index = 11)
//    private BigDecimal xjzf;
//    @ExcelProperty(value = {"城镇职工保险"}, index = 12)
//    private BigDecimal czzgbx;
//    @ExcelProperty(value = {"身份证号码"}, index = 13)
//    private String sfzhm;
//    @ExcelProperty(value = {"零售单号"}, index = 14)
//    private String lsdh;
//    @ExcelProperty(value = {"社保卡名称"}, index = 15)
//    private String sbkmc;
//    @ExcelProperty(value = {"会员名称"}, index = 16)
//    private String hymc;

}
