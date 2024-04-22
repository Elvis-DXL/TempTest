package project.base;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/19 16:45
 */
public final class DSUtil {
    //私有化构造器，禁止创建对象
    private DSUtil() {
        throw new AssertionError("Utility classes do not allow instantiation");
    }

    /****************************************以上为私有构造器，以下为常量****************************************/
    //地球半径,单位:m
    public static final double EARTH_RADIUS = 6371393;

    //时间格式化枚举
    public enum DatePattern {
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
        x_yyyy_MM_dd_EE("yyyy/MM/dd EE"),
        yyyy_MM_dd_EE("yyyy-MM-dd EE"),
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
        ;
        private final String val;

        DatePattern(String val) {
            this.val = val;
        }

        public String val() {
            return val;
        }
    }

    /*****************************************以上为常量，以下为工具函数*****************************************/
    public static LocalDateTime dayStart(LocalDateTime time) {
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime dayEnd(LocalDateTime time) {
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    public static LocalDateTime monthStart(LocalDateTime time) {
        return dayStart(time.with(TemporalAdjusters.firstDayOfMonth()));
    }

    public static LocalDateTime monthEnd(LocalDateTime time) {
        return dayEnd(time.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public static LocalDateTime yearStart(LocalDateTime time) {
        return dayStart(time.with(TemporalAdjusters.firstDayOfYear()));
    }

    public static LocalDateTime yearEnd(LocalDateTime time) {
        return dayEnd(time.with(TemporalAdjusters.lastDayOfYear()));
    }

    public static String formatTime(LocalDateTime time, String datePattern) {
        return DateTimeFormatter.ofPattern(datePattern).format(time);
    }

    public static String formatTime(LocalDateTime time, DatePattern datePattern) {
        return DateTimeFormatter.ofPattern(datePattern.val()).format(time);
    }

    public static LocalDateTime parseTime(String timeStr, String datePattern) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(datePattern));
    }

    public static LocalDateTime parseTime(String timeStr, DatePattern datePattern) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(datePattern.val()));
    }

    public static LocalDateTime dateToLocal(Date date) {
        return null != date ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    public static Date localToDate(LocalDateTime localDateTime) {
        return null != localDateTime ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static double lngLatMeter(double srcLng, double srcLat, double aimLng, double aimLat) {
        return EARTH_RADIUS * Math.acos(Math.cos(Math.toRadians(srcLat)) * Math.cos(Math.toRadians(aimLat))
                * Math.cos(Math.toRadians(srcLng) - Math.toRadians(aimLng))
                + Math.sin(Math.toRadians(srcLat)) * Math.sin(Math.toRadians(aimLat)));
    }
}
