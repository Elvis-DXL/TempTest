package tool;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * @Author : 慕君Dxl
 */
public final class DSUtil {

    private DSUtil() {
        throw new AssertionError("Utility classes do not allow instantiation");
    }

    public enum Pattern {
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
        ;

        private final String pattern;

        Pattern(String pattern) {
            this.pattern = pattern;
        }

        public String val() {
            return pattern;
        }
    }

    public LocalDateTime dayStart(LocalDateTime time) {
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime dayEnd(LocalDateTime time) {
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    public LocalDateTime monthStart(LocalDateTime time) {
        return dayStart(time.with(TemporalAdjusters.firstDayOfMonth()));
    }

    public LocalDateTime monthEnd(LocalDateTime time) {
        return dayEnd(time.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public LocalDateTime yearStart(LocalDateTime time) {
        return dayStart(time.with(TemporalAdjusters.firstDayOfYear()));
    }

    public LocalDateTime yearEnd(LocalDateTime time) {
        return dayStart(time.with(TemporalAdjusters.lastDayOfYear()));
    }
}
