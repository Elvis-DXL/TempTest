package com.elvis.test.tempcheck;

import com.elvis.commons.enums.DPEnum;
import com.elvis.commons.utils.CollUtil;
import com.elvis.commons.utils.DateUtil;
import com.elvis.commons.utils.ExcelUtil;
import com.elvis.commons.utils.StrUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/26 10:31
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        String headStr = "D:/TDDOWN/眉山市国健大药房连锁有限公司中二山店/";
        //读取数据
        List<YpmxPo> ypmxPos = ExcelUtil
                .excelToData(new FileInputStream(new File(headStr + "表1：药品报销明细（医保端）.xlsx")), YpmxPo.class);
        System.out.println("表一，药品报销明细数据读取完成。共：【" + ypmxPos.size() + "】条");

        List<XsmxPo> xsmxPos = ExcelUtil
                .excelToData(new FileInputStream(new File(headStr + "表2：销售明细（药店端）.xlsx")), XsmxPo.class);
        System.out.println("表二，销售明细数据读取完成。共：【" + xsmxPos.size() + "】条");

        List<JsdPo> jsdPos = ExcelUtil
                .excelToData(new FileInputStream(new File(headStr + "表3：结算单数据（医保数据）.xlsx")), JsdPo.class);
        System.out.println("表三，结算单数据读取完成。共：【" + jsdPos.size() + "】条");
        //数据预处理
        ypmxPos.sort(Comparator.comparing(YpmxPo::getFyfssj));
        Map<String, JsdPo> jsdMap = jsdPos.stream().collect(Collectors.toMap(JsdPo::getJsh, it -> it, (k1, k2) -> k1));
        //结果存储集合
        List<YbbzAndXsxtError> ybbzAndXsxtErrors = new ArrayList<>();
        //进行校验
        System.out.println("开始校验");
        int index = 1;
        int index2 = 1;
        Date jyStartTime = DateUtil.parseDate("2022-01-01 00:00:00", DPEnum.yyyy_MM_dd_HH_mm_ss);
        Date jyEndTime = DateUtil.parseDate("2022-12-31 23:59:59", DPEnum.yyyy_MM_dd_HH_mm_ss);
        for (YpmxPo po : ypmxPos) {
            System.out.println("第" + index + "条");
            index++;

            if (jyStartTime.getTime() > po.getFyfssj().getTime() || jyEndTime.getTime() < po.getFyfssj().getTime()) {
                continue;
            }

            Date startTime = DateUtil.addDate(po.getFyfssj(), Calendar.MINUTE, -15);
            Date endTime = DateUtil.addDate(po.getFyfssj(), Calendar.MINUTE, 15);

            JsdPo jsdPo = jsdMap.get(po.getJsh());
            if (null == jsdPo) {
                continue;
            }

            List<XsmxPo> collect = xsmxPos.stream().filter(it ->
//                    StrUtil.isNotEmpty(it.getGjybbm()) && po.getYbxmbm().equals(it.getGjybbm())
                    it.getSpmc().contains(po.getYbxmmc())
                            && startTime.getTime() <= it.getXssj().getTime()
                            && endTime.getTime() >= it.getXssj().getTime()).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(collect)) {
                continue;
            }

            ybbzAndXsxtErrors.add(consYbbzAndXsxtError(po, jsdPo));
            System.out.println("添加第" + index2 + "条违规");
            index2++;
        }
        System.out.println("数据核对完毕！共" + ybbzAndXsxtErrors.size() + "条问题数据");

        String fileName = headStr.substring(10, headStr.length() - 1);

        FileOutputStream fos = new FileOutputStream(new File(headStr + fileName + ".xlsx"));
        ExcelUtil.dataToExcel(ybbzAndXsxtErrors, YbbzAndXsxtError.class, "核算结果", fos);
        System.out.println("数据核对结果输出完毕！");
    }

    private static YbbzAndXsxtError consYbbzAndXsxtError(YpmxPo ypmxPo, JsdPo jsdPo) {
        YbbzAndXsxtError error = new YbbzAndXsxtError();

        error.setYpmc(ypmxPo.getYbxmmc());
        error.setJsdh(ypmxPo.getJsh());
        error.setSksj(ypmxPo.getFyfssj());
        error.setSkje(ypmxPo.getJe());
        error.setSkrxm(null == jsdPo ? "" : jsdPo.getXm());
        error.setSfzh(null == jsdPo ? "" : jsdPo.getSfzh());

        return error;
    }
}
