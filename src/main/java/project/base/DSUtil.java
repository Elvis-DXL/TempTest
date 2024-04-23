package project.base;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    //零到九字符串集合
    public static final List<String> ZERO_TO_NINE = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    //时间格式化枚举
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

        Pattern(String val) {
            this.val = val;
        }

        public String val() {
            return val;
        }
    }

    //特殊符号枚举
    public enum Symbol {
        DH(","),
        FH(";"),
        XHX("_"),
        ZHX("-"),
        YWD("."),
        BFH("%"),
        MYF("$"),
        RMB("￥"),
        ADF("@"),
        ZXX("/"),
        ;
        private final String val;

        Symbol(String val) {
            this.val = val;
        }

        public String val() {
            return val;
        }

        /***************************************工具方法*************************************/
        public static String likeSQL(String aimStr) {
            return BFH.val + aimStr + BFH.val;
        }

        public static String likeSQL(String aimStr, Symbol symbol) {
            return likeSQL(symbol.val + aimStr + symbol.val);
        }

        public static String leftLikeSQL(String aimStr) {
            return aimStr + BFH.val;
        }

        public static String rightLikeSQL(String aimStr) {
            return BFH.val + aimStr;
        }

        public Boolean included(String aimStr) {
            return null != aimStr && aimStr.length() != 0 && aimStr.trim().contains(this.val);
        }

        public Boolean notIncluded(String aimStr) {
            return !this.included(aimStr);
        }

        public Boolean spliceSrcContainAim(String spliceSrc, String aim) {
            return null != spliceSrc && spliceSrc.length() != 0 && null != aim && aim.length() != 0
                    && spliceSrc.contains(this.val + aim + this.val);
        }

        public Boolean spliceSrcNotContainAim(String spliceSrc, String aim) {
            return !this.spliceSrcContainAim(spliceSrc, aim);
        }

        public List<String> split(String aimStr) {
            if (this.notIncluded(aimStr)) {
                return new ArrayList<>();
            }
            StringTokenizer st = new StringTokenizer(aimStr, this.val);
            List<String> result = new ArrayList<>();
            while (st.hasMoreTokens()) {
                result.add(st.nextToken());
            }
            return result;
        }

        public List<Long> splitToLong(String aimStr) {
            if (this.notIncluded(aimStr)) {
                return new ArrayList<>();
            }
            StringTokenizer st = new StringTokenizer(aimStr, this.val);
            List<Long> result = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                result.add(Long.valueOf(temp));
            }
            return result;
        }

        public List<Integer> splitToInteger(String aimStr) {
            if (this.notIncluded(aimStr)) {
                return new ArrayList<>();
            }
            StringTokenizer st = new StringTokenizer(aimStr, this.val);
            List<Integer> result = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                result.add(Integer.valueOf(temp));
            }
            return result;
        }

        public String join(Collection<String> srcList) {
            return this.join(srcList, true);
        }

        public <T, R> String join(Collection<T> srcList, Function<? super T, ? extends R> function) {
            return this.join(srcList, function, true);
        }

        public String join(Collection<String> srcList, boolean includeStartAndEnd) {
            if (null == srcList || srcList.isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (String str : srcList) {
                sb.append(str);
                sb.append(this.val);
            }
            String result = sb.toString();
            result = result.substring(0, result.length() - this.val.length());
            if (includeStartAndEnd) {
                result = this.val + result + this.val;
            }
            return result;
        }

        public <T, R> String join(Collection<T> srcList, Function<? super T, ? extends R> function, boolean includeStartAndEnd) {
            if (null == srcList || srcList.size() == 0) {
                return null;
            }
            List<String> strList = srcList.stream().map(function).map(Object::toString).collect(Collectors.toList());
            return this.join(strList, includeStartAndEnd);
        }
    }

    //正则表达式
    public enum Regex {
        ID_CARD("(^[0-9]{18}$)|(^[0-9]{17}(X|x)$)", "身份证号码"),
        PHONE("(^(1)[0-9]{10}$)", "手机号"),
        ;

        private final String regexStr;
        private final String desc;

        Regex(String regexStr, String desc) {
            this.regexStr = regexStr;
            this.desc = desc;
        }

        public boolean verify(String aimStr) {
            return null != aimStr && aimStr.length() != 0 && aimStr.matches(this.regexStr);
        }

        public boolean verifyFail(String aimStr) {
            return !this.verify(aimStr);
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

    public static String formatTime(LocalDateTime time, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(time);
    }

    public static String formatTime(LocalDateTime time, Pattern pattern) {
        return DateTimeFormatter.ofPattern(pattern.val()).format(time);
    }

    public static LocalDateTime parseTime(String timeStr, String pattern) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseTime(String timeStr, Pattern pattern) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern.val()));
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

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static void isNotEmpty(Collection<?> coll, Consumer<Collection<?>> consumer) {
        if (isEmpty(coll)) {
            return;
        }
        consumer.accept(coll);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static void isNotEmpty(String str, Consumer<String> consumer) {
        if (isEmpty(str)) {
            return;
        }
        consumer.accept(str);
    }

    public static List<Field> allFields(Class clazz) {
        List<Field> result = new ArrayList<>();
        do {
            Field[] fields = clazz.getDeclaredFields();
            if (null != fields && fields.length > 0) {
                result.addAll(Arrays.asList(fields));
            }
            clazz = clazz.getSuperclass();
        } while (null != clazz && !clazz.equals(Object.class));
        return result;
    }

    public static <T, K> K copySomeFields(T src, K aim, String... fields) {
        Map<String, Field> srcMap = allFields(src.getClass())
                .stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        Map<String, Field> aimMap = allFields(aim.getClass())
                .stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        List<String> fieldList = Arrays.asList(fields);
        if (fieldList.size() == 0) {
            fieldList = new ArrayList<>(srcMap.keySet());
        }
        for (String field : fieldList) {
            Field srcField = srcMap.get(field);
            Field aimField = aimMap.get(field);
            if (null == srcField || null == aimField) {
                continue;
            }
            srcField.setAccessible(true);
            aimField.setAccessible(true);
            try {
                aimField.set(aim, srcField.get(src));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                srcField.setAccessible(false);
                aimField.setAccessible(false);
            }
        }
        return aim;
    }

    public static String randomNumberStrByLength(int length) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        while (count <= length) {
            sb.append(randomGetOne(ZERO_TO_NINE));
            count++;
        }
        return sb.toString();
    }

    public static String intToStrByLength(int num, Integer length) {
        if (null == length) {
            return num + "";
        }
        String str = num + "";
        while (str.length() < length) {
            str = "0".concat(str);
        }
        return str;
    }

    public static Double doubleFmt(Double value, Integer num) {
        if (value == null) {
            return null;
        }
        if (num == null || num < 1) {
            return value;
        }
        StringBuilder builder = new StringBuilder("#.");
        for (int i = 0; i < num; i++) {
            builder.append("0");
        }
        DecimalFormat df = new DecimalFormat(builder.toString());
        return Double.parseDouble(df.format(value));
    }

    public static String UUID32() {
        return UUID36().replace("-", "");
    }

    public static String UUID36() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString();
    }

    public static <T> T randomGetOne(List<T> srcList) {
        return srcList.get(ThreadLocalRandom.current().nextInt(srcList.size()));
    }

    public static <T, K> T mapGet(Map<K, T> srcMap, K aimKey) {
        return null == srcMap || srcMap.isEmpty() || null == aimKey ? null : srcMap.get(aimKey);
    }
}
