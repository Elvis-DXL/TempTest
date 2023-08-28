package newproject.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 静态工具类
 *
 * @Author : 慕君Dxl
 * @CreateTime : 2023/8/24 17:50
 */
public final class STool {

    //私有化构造器，不允许创建对象 ↓
    private STool() {
        throw new AssertionError("Utility classes do not allow instantiation");
    }

    //日期时间格式化参数常量 ↓
    public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String yyyyMMddHH = "yyyyMMddHH";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMM = "yyyyMM";
    public static final String HHmmssSSS = "HHmmssSSS";
    public static final String HHmmss = "HHmmss";
    public static final String HHmm = "HHmm";
    public static final String c_yyyy_MM_dd_HH_mm_ss_SSS = "yyyy年MM月dd日HH时mm分ss秒SSS毫秒";
    public static final String c_yyyy_MM_dd_HH_mm_ss = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String c_yyyy_MM_dd_HH_mm = "yyyy年MM月dd日HH时mm分";
    public static final String c_yyyy_MM_dd_HH = "yyyy年MM月dd日HH时";
    public static final String c_yyyy_MM_dd = "yyyy年MM月dd日";
    public static final String c_yyyy_MM = "yyyy年MM月";
    public static final String c_yyyy = "yyyy年";
    public static final String c_HH_mm_ss_SSS = "HH时mm分ss秒SSS毫秒";
    public static final String c_HH_mm_ss = "HH时mm分ss秒";
    public static final String c_HH_mm = "HH时mm分";
    public static final String c_yyyy_MM_dd_EE = "yyyy年MM月dd日 EE";
    public static final String c_EE = "EE";
    public static final String x_yyyy_MM_dd_HH_mm_ss_SSS = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String x_yyyy_MM_dd_HH_mm_ss = "yyyy/MM/dd HH:mm:ss";
    public static final String x_yyyy_MM_dd_HH_mm = "yyyy/MM/dd HH:mm";
    public static final String x_yyyy_MM_dd_HH = "yyyy/MM/dd HH";
    public static final String x_yyyy_MM_dd = "yyyy/MM/dd";
    public static final String x_yyyy_MM = "yyyy/MM";
    public static final String xh_yyyy_MM_dd_HH = "yyyy_MM_dd_HH";
    public static final String xh_yyyy_MM_dd = "yyyy_MM_dd";
    public static final String xh_yyyy_MM = "yyyy_MM";
    public static final String yyyy = "yyyy";
    public static final String MM = "MM";
    public static final String dd = "dd";
    public static final String HH = "HH";
    public static final String mm = "mm";
    public static final String ss = "ss";
    public static final String SSS = "SSS";

    //正则表达式常量 ↓
    public static final String RX_ID_CARD = "(^[0-9]{18}$)|(^[0-9]{17}(X|x)$)";

    //常量定义 ↓
    public static final double EARTH_RADIUS = 6371393;//地球平均半径(m)

    //静态工具方法 ↓

    /**
     * 判断字符序列是否是null或者空字符串
     *
     * @param cs 字符序列
     * @return 结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:33
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符序列是否非空
     *
     * @param cs 字符序列
     * @return 结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:34
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符串是否非空，非空执行相关业务
     *
     * @param str      字符串
     * @param consumer 要执行的业务
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:35
     */
    public static void isNotEmpty(String str, Consumer<String> consumer) {
        if (isEmpty(str)) {
            return;
        }
        consumer.accept(str);
    }

    /**
     * 判断集合是否是空
     *
     * @param coll 集合数据
     * @return 结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:36
     */
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 判断集合是否是非空
     *
     * @param coll 集合数据
     * @return 结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:37
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断集合是否是非空，非空执行相关业务
     *
     * @param coll     集合数据
     * @param consumer 要执行的业务
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:37
     */
    public static void isNotEmpty(Collection<?> coll, Consumer<Collection<?>> consumer) {
        if (isEmpty(coll)) {
            return;
        }
        consumer.accept(coll);
    }

    /**
     * 随机从集合中取出一个元素
     *
     * @param srcList 源数据集合
     * @param <T>     泛型
     * @return 随机取出的元素
     * @author 慕君Dxl
     * @createTime 2023/8/24 18:10
     */
    public static <T> T randomGetOne(List<T> srcList) {
        return srcList.get((int) (Math.random() * srcList.size()));
    }

    /**
     * int转换为指定长度的字符串
     *
     * @param num    目标字符串
     * @param length 长度
     * @return 结果字符串
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:40
     */
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

    /**
     * 获取指定类的所有字段属性
     *
     * @param clazz 目标类
     * @return 字段属性集合
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:41
     */
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

    /**
     * 拷贝对象
     *
     * @param srcObj       源对象
     * @param aimObj       目标对象
     * @param ignoreFields 忽略的字段
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:49
     */
    public static void copyFields(Object srcObj, Object aimObj, String... ignoreFields) {
        List<String> ignore = new ArrayList<>();
        if (null != ignoreFields && ignoreFields.length > 0) {
            ignore.addAll(Arrays.asList(ignoreFields));
        }
        List<Field> srcFields = allFields(srcObj.getClass());
        List<Field> aimFields = allFields(aimObj.getClass());
        if (isEmpty(srcFields) || isEmpty(aimFields)) {
            return;
        }
        Map<String, Field> srcMap = srcFields.stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        for (Field aimField : aimFields) {
            if (ignore.contains(aimField.getName())) {
                continue;
            }
            Field srcField = srcMap.get(aimField.getName());
            if (null == srcField) {
                continue;
            }
            Object val = null;
            srcField.setAccessible(true);
            try {
                val = srcField.get(srcObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            srcField.setAccessible(false);
            aimField.setAccessible(true);
            try {
                aimField.set(aimObj, val);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            aimField.setAccessible(false);
        }
    }

    /**
     * 通过类创建新对象
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 对象
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:50
     */
    public static <T> T newInstance(Class<T> clazz) {
        if (null == clazz) {
            throw new IllegalArgumentException("Class must not be null");
        }
        if (clazz.isInterface()) {
            throw new IllegalArgumentException("Specified class is an interface");
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 通过对象创建新对象
     *
     * @param srcObj 源对象
     * @param clazz  新对象类信息
     * @param <T>    泛型
     * @param <K>    泛型
     * @return 新对象
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:51
     */
    public static <T, K> T newByObj(K srcObj, Class<T> clazz) {
        T aimObj = newInstance(clazz);
        copyFields(srcObj, aimObj);
        return aimObj;
    }

    /**
     * 改变集合元素类型
     *
     * @param srcList 源集合
     * @param clazz   目标类信息
     * @param <T>     泛型
     * @param <K>     泛型
     * @return 新集合
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:51
     */
    public static <T, K> List<T> classChange(List<K> srcList, Class<T> clazz) {
        if (isEmpty(srcList)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (K item : srcList) {
            result.add(newByObj(item, clazz));
        }
        return result;
    }

    /**
     * 集合去重
     *
     * @param srcList 源集合
     * @param <T>     泛型
     * @return 去重后集合
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:15
     */
    public static <T> List<T> listDistinct(List<T> srcList) {
        if (null == srcList) {
            return new ArrayList<>();
        }
        return srcList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 四舍五入格式化double小数，指定小数点后长度
     *
     * @param aimValue 目标值
     * @param num      小数点位数
     * @return 格式化后结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 9:53
     */
    public static Double doubleFmt(Double aimValue, Integer num) {
        if (aimValue == null) {
            return 0D;
        }
        if (num == null || num < 1) {
            return aimValue;
        }
        StringBuilder builder = new StringBuilder("#.");
        for (int i = 0; i < num; i++) {
            builder.append("0");
        }
        DecimalFormat df = new DecimalFormat(builder.toString());
        return Double.parseDouble(df.format(aimValue));
    }

    /**
     * Date对象转换成Calendar对象 传入空值默认为当前时间
     *
     * @param date 目标date
     * @return Calendar对象
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:03
     */
    public static Calendar dateToCalendar(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 时间日期计算
     *
     * @param date         目标
     * @param calendarType 计算类型
     * @param num          计算数量
     * @return 计算后结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:04
     */
    public static Date addDate(Date date, int calendarType, int num) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(calendarType, num);
        return calendar.getTime();
    }

    /**
     * 格式化日期时间
     *
     * @param date    目标
     * @param pattern 格式
     * @return 格式化后字符串
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:05
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 解析时间日期字符串
     *
     * @param dateStr 目标
     * @param pattern 格式
     * @return 解下结果
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:06
     */
    public static Date parseDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取指定日期的当月总天数
     *
     * @param date 目标
     * @return 当月总天数
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:06
     */
    public static int monthDaysByDate(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取指定时间的当天开始时间
     *
     * @param date 目标
     * @return 当天开始时间
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:07
     */
    public static Date dateStartTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的当天结束时间
     *
     * @param date 目标
     * @return 当天结束时间
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:08
     */
    public static Date dateEndTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的当月开始时间
     *
     * @param date 目标
     * @return 当月开始时间
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:08
     */
    public static Date dateCurrMonthStartTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, 1);
        return dateStartTime(calendar.getTime());
    }

    /**
     * 获取指定时间的当月结束时间
     *
     * @param date 目标
     * @return 当月结束时间
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:09
     */
    public static Date dateCurrMonthEndTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, monthDaysByDate(date));
        return dateEndTime(calendar.getTime());
    }

    /**
     * 获取指定时间的当年开始时间
     *
     * @param date 目标
     * @return 当年开始时间
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:09
     */
    public static Date dateCurrYearStartTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        return dateStartTime(calendar.getTime());
    }

    /**
     * 获取指定时间的当年结束时间
     *
     * @param date 目标
     * @return 当年结束时间
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:10
     */
    public static Date dateCurrYearEndTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        return dateEndTime(calendar.getTime());
    }

    /**
     * 计算两个经纬度之间的距离
     *
     * @param srcLng 起点经度
     * @param srcLat 起点纬度
     * @param aimLng 终点经度
     * @param aimLat 终点纬度
     * @return 距离单位（m）
     * @author 慕君Dxl
     * @createTime 2023/8/25 10:10
     */
    public static double lngLatDistance(double srcLng, double srcLat, double aimLng, double aimLat) {
        double radiansAX = Math.toRadians(srcLng);
        double radiansAY = Math.toRadians(srcLat);
        double radiansBX = Math.toRadians(aimLng);
        double radiansBY = Math.toRadians(aimLat);

        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
        double acos = Math.acos(cos);
        return EARTH_RADIUS * acos;
    }
}
