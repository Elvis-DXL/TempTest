package newproject.enums;

/**
 * 时间日期格式定义枚举
 *
 * @author : Elvis
 * @since : 2022/3/3 17:33
 */
public enum DatePattern {
    //枚举定义
    yyyy_MM_dd_HH_mm_ss_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
    yyyy_MM_dd_HH("yyyy-MM-dd HH"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyyy_MM("yyyy-MM"),
    HH_mm_ss("HH:mm:ss"),
    HH_mm("HH:mm"),
    yyyyMMddHHmmssSSS("yyyyMMddHHmmssSSS"),
    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    yyyyMMddHHmm("yyyyMMddHHmm"),
    yyyyMMddHH("yyyyMMddHH"),
    yyyyMMdd("yyyyMMdd"),
    yyyyMM("yyyyMM"),
    HHmmssSSS("HHmmssSSS"),
    HHmmss("HHmmss"),
    HHmm("HHmm"),
    c_yyyy_MM_dd_HH_mm_ss_SSS("yyyy年MM月dd日HH时mm分ss秒SSS毫秒"),
    c_yyyy_MM_dd_HH_mm_ss("yyyy年MM月dd日HH时mm分ss秒"),
    c_yyyy_MM_dd_HH_mm("yyyy年MM月dd日HH时mm分"),
    c_yyyy_MM_dd_HH("yyyy年MM月dd日HH时"),
    c_yyyy_MM_dd("yyyy年MM月dd日"),
    c_yyyy_MM("yyyy年MM月"),
    c_yyyy("yyyy年"),
    c_HH_mm_ss_SSS("HH时mm分ss秒SSS毫秒"),
    c_HH_mm_ss("HH时mm分ss秒"),
    c_HH_mm("HH时mm分"),
    c_yyyy_MM_dd_EE("yyyy年MM月dd日 EE"),
    c_EE("EE"),
    x_yyyy_MM_dd_HH_mm_ss_SSS("yyyy/MM/dd HH:mm:ss.SSS"),
    x_yyyy_MM_dd_HH_mm_ss("yyyy/MM/dd HH:mm:ss"),
    x_yyyy_MM_dd_HH_mm("yyyy/MM/dd HH:mm"),
    x_yyyy_MM_dd_HH("yyyy/MM/dd HH"),
    x_yyyy_MM_dd("yyyy/MM/dd"),
    x_yyyy_MM("yyyy/MM"),
    xh_yyyy_MM_dd_HH("yyyy_MM_dd_HH"),
    xh_yyyy_MM_dd("yyyy_MM_dd"),
    xh_yyyy_MM("yyyy_MM"),
    yyyy("yyyy"),
    MM("MM"),
    dd("dd"),
    HH("HH"),
    mm("mm"),
    ss("ss"),
    SSS("SSS"),

    //定义结束
    ;

    private final String value;

    DatePattern(String value) {
        this.value = value;
    }

    public String val() {
        return value;
    }
}
