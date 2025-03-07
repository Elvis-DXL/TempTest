package basejpa.util;

import com.google.gson.Gson;
import com.zx.core.base.form.OrderItem;
import com.zx.core.tool.utils.JsonUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:56
 */
public final class DSUtil {
    private DSUtil() {
        throw new AssertionError("Utility classes do not allow instantiation");
    }

    private static final double EARTH_RADIUS_METER = 6371393D;

    private static final List<String> ZERO_TO_NINE = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

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
        xh_yyyy_MM("yyyy_MM");

        private final String val;

        Pattern(String val) {
            this.val = val;
        }

        public String val() {
            return val;
        }
    }

    public enum Symbol {
        DH(","), FH(";"), XH("*"), SX("|"), JH("#"), BL("~"), WH("?"), DY(">"), XY("<"), DENGY("="), JIAH("+"),
        XHX("_"), ZHX("-"), ZKH("("), YKH(")"), YWD("."), ZWZKH("（"), ZWYKH("）"), BFH("%"), MYF("$"), RMB("￥"),
        ADF("@"), ZXX("/"), HAT("^"), ZDA("&"), ZZKH("["), YZKH("]"), ZDKH("{"), YDKH("}"), ZWZZKH("【"), ZWYZKH("】");

        private final String val;

        Symbol(String val) {
            this.val = val;
        }

        public String val() {
            return val;
        }

        public static String likeSQL(String aimStr) {
            return BFH.val() + aimStr + BFH.val();
        }

        public static String likeSQL(String aimStr, Symbol symbol) {
            return likeSQL(symbol.val() + aimStr + symbol.val());
        }

        public static String leftLikeSQL(String aimStr) {
            return aimStr + BFH.val();
        }

        public static String rightLikeSQL(String aimStr) {
            return BFH.val() + aimStr;
        }

        public Boolean included(String aimStr) {
            return EmptyTool.isNotEmpty(aimStr) && aimStr.contains(val());
        }

        public Boolean notIncluded(String aimStr) {
            return !included(aimStr);
        }

        public Boolean spliceSrcContainAim(String spliceSrc, String aim) {
            return EmptyTool.isNotEmpty(spliceSrc) && EmptyTool.isNotEmpty(aim) && spliceSrc.contains(val() + aim + val());
        }

        public Boolean spliceSrcNotContainAim(String spliceSrc, String aim) {
            return !spliceSrcContainAim(spliceSrc, aim);
        }

        public List<String> split(String aimStr) {
            if (EmptyTool.isEmpty(aimStr)) {
                return new ArrayList<>();
            }
            StringTokenizer st = new StringTokenizer(aimStr, val());
            List<String> result = new ArrayList<>();
            while (st.hasMoreTokens()) {
                result.add(st.nextToken());
            }
            return result;
        }

        public List<Long> splitToLong(String aimStr) {
            if (EmptyTool.isEmpty(aimStr)) {
                return new ArrayList<>();
            }
            StringTokenizer st = new StringTokenizer(aimStr, val());
            List<Long> result = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                result.add(Long.valueOf(temp));
            }
            return result;
        }

        public List<Integer> splitToInteger(String aimStr) {
            if (EmptyTool.isEmpty(aimStr)) {
                return new ArrayList<>();
            }
            StringTokenizer st = new StringTokenizer(aimStr, val());
            List<Integer> result = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                result.add(Integer.valueOf(temp));
            }
            return result;
        }

        public <T extends Serializable> String bothSidesAdd(T aim) {
            return null == aim ? null : (val() + aim + val());
        }

        public String join(Collection<String> srcList) {
            return join(srcList, true);
        }

        public <T, K> String join(Collection<T> srcList, Function<? super T, ? extends K> function) {
            return join(srcList, function, true);
        }

        public String join(Collection<String> srcList, boolean includeStartAndEnd) {
            if (EmptyTool.isEmpty(srcList)) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (String str : srcList) {
                sb.append(str);
                sb.append(val());
            }
            String result = sb.toString();
            result = result.substring(0, result.length() - val().length());
            if (includeStartAndEnd) {
                result = val() + result + val();
            }
            return result;
        }

        public <T, K> String join(Collection<T> srcList, Function<? super T, ? extends K> function, boolean includeStartAndEnd) {
            return EmptyTool.isEmpty(srcList) ? null :
                    join(srcList.stream().filter(Objects::nonNull).map(function).filter(Objects::nonNull).map(Object::toString)
                            .collect(Collectors.toList()), includeStartAndEnd);
        }
    }

    public enum Regex {
        ID_CARD("(^[0-9]{18}$)|(^[0-9]{17}(Ⅹ|X|x)$)", "身份证号码"),
        MOBILE_PHONE("(^1[0-9]{10}$)", "手机号"),
        LNG_LAT("(^[0-9]{1,3}$)|(^[0-9]{1,3}[\\.]{1}$)|(^[0-9]{1,3}[\\.]{1}[0-9]+$)", "经纬度");

        private final String regexStr;
        private final String desc;

        Regex(String regexStr, String desc) {
            this.regexStr = regexStr;
            this.desc = desc;
        }

        public String getRegexStr() {
            return regexStr;
        }

        public String getDesc() {
            return desc;
        }

        public static boolean verify(String aimStr, String regexStr) {
            return EmptyTool.isNotEmpty(aimStr) && EmptyTool.isNotEmpty(regexStr) && aimStr.matches(regexStr);
        }

        public static boolean verifyFail(String aimStr, String regexStr) {
            return !verify(aimStr, regexStr);
        }

        public boolean verify(String aimStr) {
            return EmptyTool.isNotEmpty(aimStr) && aimStr.matches(getRegexStr());
        }

        public boolean verifyFail(String aimStr) {
            return !verify(aimStr);
        }
    }

    public enum Gender {
        NV(0, "女"), NAN(1, "男");

        private final Integer val;
        private final String desc;

        Gender(Integer val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public Integer val() {
            return val;
        }

        public String desc() {
            return desc;
        }
    }

    public static Double lngLatMeter(Double srcLng, Double srcLat, Double aimLng, Double aimLat) {
        return null == srcLng || null == srcLat || null == aimLng || null == aimLat ? null
                : EARTH_RADIUS_METER * Math.acos(Math.cos(Math.toRadians(srcLat)) * Math.cos(Math.toRadians(aimLat))
                * Math.cos(Math.toRadians(srcLng) - Math.toRadians(aimLng))
                + Math.sin(Math.toRadians(srcLat)) * Math.sin(Math.toRadians(aimLat)));
    }

    public static Double lngLatMeter(String srcLng, String srcLat, String aimLng, String aimLat) {
        return EmptyTool.isEmpty(srcLng) || EmptyTool.isEmpty(srcLat)
                || EmptyTool.isEmpty(aimLng) || EmptyTool.isEmpty(aimLat) ? null
                : lngLatMeter(Double.parseDouble(srcLng), Double.parseDouble(srcLat),
                Double.parseDouble(aimLng), Double.parseDouble(aimLat));
    }

    public static <T> T newInstance(Class<T> clazz) {
        trueThrow(null == clazz, new NullPointerException("class must not be null"));
        trueThrow(clazz.isInterface(), new IllegalArgumentException("specified class is an interface"));
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<Field> classAllFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        do {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length > 0) {
                result.addAll(Arrays.asList(fields));
            }
            clazz = clazz.getSuperclass();
        } while (null != clazz && !clazz.equals(Object.class));
        return result;
    }

    public static <T, K> K newByObj(Class<K> clazz, T srcObj, String... fields) {
        return null == clazz || null == srcObj ? null : copySomeFields(srcObj, newInstance(clazz), fields);
    }

    public static <T, K> K copySomeFields(T src, K aim, String... fields) {
        if (null == src || null == aim) {
            return aim;
        }
        Map<String, Field> srcMap = ListTool.listToMap(classAllFields(src.getClass()), Field::getName, it -> it);
        Map<String, Field> aimMap = ListTool.listToMap(classAllFields(aim.getClass()), Field::getName, it -> it);
        List<String> fieldList = Arrays.asList(fields);
        if (EmptyTool.isEmpty(fieldList)) {
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
                e.printStackTrace();
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
        return EmptyTool.isEmpty(srcList) ? null : srcList.get(ThreadLocalRandom.current().nextInt(srcList.size()));
    }

    public static <T, V> V objGet(T srcObj, Function<? super T, ? extends V> function) {
        return null == srcObj ? null : function.apply(srcObj);
    }

    public static <T, K> T mapGet(Map<K, T> srcMap, K aimKey) {
        return null == srcMap || srcMap.isEmpty() ? null : srcMap.get(aimKey);
    }

    public static <T, K, V> V mapObjGetField(Map<K, T> srcMap, K aimKey, Function<? super T, ? extends V> function) {
        return null == srcMap || srcMap.isEmpty() || null == srcMap.get(aimKey) ? null : function.apply(srcMap.get(aimKey));
    }

    public static String hypStr(String aimStr, int start, int mid, int end) {
        if (EmptyTool.isEmpty(aimStr) || aimStr.length() < Math.max(start, end)) {
            return aimStr;
        }
        String midStr = "";
        while (midStr.length() < mid) {
            midStr = midStr.concat("*");
        }
        return aimStr.substring(0, start) + midStr + aimStr.substring(aimStr.length() - end);
    }

    public static <T> void judgeAdd(List<T> aimList, T aimObj) {
        trueThrow(null == aimList, new NullPointerException("aimList must not be null"));
        if (null == aimObj || aimList.contains(aimObj)) {
            return;
        }
        aimList.add(aimObj);
    }

    public static <T> void judgeAddAll(List<T> aimList, List<T> aims) {
        trueThrow(null == aimList, new NullPointerException("aimList must not be null"));
        if (EmptyTool.isEmpty(aims)) {
            return;
        }
        for (T item : aims) {
            if (null == item || aimList.contains(item)) {
                continue;
            }
            aimList.add(item);
        }
    }

    public static Integer birthdayToCurrAge(LocalDate birthday) {
        return birthdayToAgeByTime(birthday, LocalDate.now());
    }

    public static Integer birthdayToAgeByTime(LocalDate birthday, LocalDate aimTime) {
        if (null == birthday || null == aimTime) {
            return null;
        }
        return Math.max(aimTime.getMonthValue() < birthday.getMonthValue() ? aimTime.getYear() - birthday.getYear() - 1
                : (aimTime.getMonthValue() > birthday.getMonthValue() ? aimTime.getYear() - birthday.getYear()
                : (aimTime.getDayOfMonth() < birthday.getDayOfMonth() ?
                aimTime.getYear() - birthday.getYear() - 1 : aimTime.getYear() - birthday.getYear())), 0);
    }

    public static Integer birthdayStrToCurrAge(String birthdayStr) {
        return birthdayStrToAgeByTime(birthdayStr, LocalDate.now());
    }

    public static Integer birthdayStrToAgeByTime(String birthdayStr, LocalDate aimTime) {
        if (EmptyTool.isEmpty(birthdayStr)) {
            return null;
        }
        LocalDate birthday;
        try {
            birthday = TimeTool.parseLD(birthdayStr, Pattern.yyyy_MM_dd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return birthdayToAgeByTime(birthday, aimTime);
    }

    public static Integer minLackOrNext(List<Integer> srcList) {
        srcList = ListTool.listNonNull(srcList);
        if (EmptyTool.isEmpty(srcList)) {
            return 1;
        }
        srcList.sort(Integer::compare);
        Integer max = srcList.get(srcList.size() - 1);
        for (int idx = 1; idx <= max; idx++) {
            if (srcList.contains(idx)) {
                continue;
            }
            return idx;
        }
        return max + 1;
    }

    public static <T extends RuntimeException> void trueThrow(boolean flag, T ex) {
        if (!flag) {
            return;
        }
        throw ex;
    }

    public static <T> void trueDo(boolean flag, T obj, Consumer<T> consumer) {
        if (!flag) {
            return;
        }
        consumer.accept(obj);
    }

    public static <T> void judgeDo(boolean flag, T obj, Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
        if (flag) {
            trueConsumer.accept(obj);
        } else {
            falseConsumer.accept(obj);
        }
    }

    public static <T> String toJson(T obj) {
        return null == obj ? null : JsonUtil.toJson(obj);
    }

    public static <T> T parseObj(String str, Class<T> clazz) {
        return EmptyTool.isEmpty(str) ? null : JsonUtil.parse(str, clazz);
    }

    public static <T> List<T> parseList(String str, Class<T> clazz) {
        return EmptyTool.isEmpty(str) ? new ArrayList<>() : JsonUtil.parseArray(str, clazz);
    }

    public static String fileExtName(String fileName) {
        if (EmptyTool.isEmpty(fileName)) {
            return "";
        }
        int indexOf = fileName.lastIndexOf(".");
        return indexOf == -1 ? "" : fileName.substring(indexOf + 1);
    }

    public final static class ListTool {
        private ListTool() {
            throw new AssertionError("Tool classes do not allow instantiation");
        }

        public static <T, K, U> Map<K, U> listToMap(List<T> srcList,
                                                    Function<? super T, ? extends K> keyMapper,
                                                    Function<? super T, ? extends U> valueMapper) {
            return EmptyTool.isEmpty(srcList) ?
                    new HashMap<>() : srcList.stream().collect(Collectors.toMap(keyMapper, valueMapper, (k1, k2) -> k1));
        }

        public static <T, K> Map<K, List<T>> listGroup(List<T> srcList, Function<? super T, ? extends K> keyMapper) {
            return EmptyTool.isEmpty(srcList) ? new HashMap<>() : srcList.stream().collect(Collectors.groupingBy(keyMapper));
        }

        public static <T, V> List<V> listMap(List<T> srcList, Function<? super T, ? extends V> mapper) {
            return EmptyTool.isEmpty(srcList) ? new ArrayList<>() : srcList.stream().map(mapper).collect(Collectors.toList());
        }

        public static <T> List<T> listFilter(List<T> srcList, Predicate<? super T> predicate) {
            return EmptyTool.isEmpty(srcList) ? new ArrayList<>() : srcList.stream().filter(predicate).collect(Collectors.toList());
        }

        public static <T> List<T> listDistinct(List<T> srcList) {
            return EmptyTool.isEmpty(srcList) ? new ArrayList<>() : srcList.stream().distinct().collect(Collectors.toList());
        }

        public static <T> List<T> listNonNull(List<T> srcList) {
            return listFilter(srcList, Objects::nonNull);
        }

        public static <T, V> List<V> listGetField(List<T> srcList, Function<? super T, ? extends V> mapper) {
            return EmptyTool.isEmpty(srcList) ? new ArrayList<>() :
                    srcList.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
        }

        public static <T, V> List<V> listGetFieldNonNull(List<T> srcList, Function<? super T, ? extends V> mapper) {
            return EmptyTool.isEmpty(srcList) ? new ArrayList<>() :
                    srcList.stream().filter(Objects::nonNull).map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
        }

        public static <T> List<T> listNonNullDistinct(List<T> srcList) {
            return EmptyTool.isEmpty(srcList) ? new ArrayList<>() :
                    srcList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        }

        public static <T, K> List<T> listClassChange(List<K> srcList, Class<T> clazz, String... fields) {
            srcList = listNonNull(srcList);
            if (EmptyTool.isEmpty(srcList) || null == clazz) {
                return new ArrayList<>();
            }
            List<T> result = new ArrayList<>();
            Map<String, Field> srcMap = listToMap(classAllFields(srcList.get(0).getClass()), Field::getName, it -> it);
            Map<String, Field> aimMap = listToMap(classAllFields(clazz), Field::getName, it -> it);
            List<String> fieldList = Arrays.asList(fields);
            if (EmptyTool.isEmpty(fieldList)) {
                fieldList = new ArrayList<>(srcMap.keySet());
            }
            for (K src : srcList) {
                T aim = newInstance(clazz);
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
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } finally {
                        srcField.setAccessible(false);
                        aimField.setAccessible(false);
                    }
                }
                result.add(aim);
            }
            return result;
        }

        public static <T> boolean containsAnyOne(List<T> srcList, List<T> aimList) {
            if (EmptyTool.isEmpty(srcList) || EmptyTool.isEmpty(aimList)) {
                return false;
            }
            for (T it : aimList) {
                if (srcList.contains(it)) {
                    return true;
                }
            }
            return false;
        }
    }

    public final static class JPATool {
        private JPATool() {
            throw new AssertionError("Tool classes do not allow instantiation");
        }

        public static javax.persistence.criteria.Predicate tjlToPredicate(List<javax.persistence.criteria.Predicate> tjList,
                                                                          CriteriaQuery<?> query) {
            javax.persistence.criteria.Predicate[] tjPredicate = new javax.persistence.criteria.Predicate[tjList.size()];
            return query.where(tjList.toArray(tjPredicate)).getRestriction();
        }

        public static javax.persistence.criteria.Predicate tjlToPredicate(List<javax.persistence.criteria.Predicate> tjList,
                                                                          OrderItem defaultSort, List<OrderItem> sortList,
                                                                          Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            javax.persistence.criteria.Predicate[] tjPredicate = new javax.persistence.criteria.Predicate[tjList.size()];
            return query.where(tjList.toArray(tjPredicate)).orderBy(getOrderArray(defaultSort, sortList, root, cb))
                    .getRestriction();
        }

        public static javax.persistence.criteria.Predicate listToOnePredicate(List<javax.persistence.criteria.Predicate> list,
                                                                              CriteriaBuilder cb, boolean isAnd) {
            javax.persistence.criteria.Predicate[] predicateArr = new javax.persistence.criteria.Predicate[list.size()];
            return isAnd ? cb.and(list.toArray(predicateArr)) : cb.or(list.toArray(predicateArr));
        }

        private static Order[] getOrderArray(OrderItem defaultSort, List<OrderItem> sortList, Root<?> root, CriteriaBuilder cb) {
            List<Order> orderList = new ArrayList<>();
            if (EmptyTool.isEmpty(sortList)) {
                orderList.add(defaultSort.isAsc() ?
                        cb.asc(root.get(defaultSort.getColumn())) : cb.desc(root.get(defaultSort.getColumn())));
            } else {
                for (OrderItem item : sortList) {
                    orderList.add(item.isAsc() ? cb.asc(root.get(item.getColumn())) : cb.desc(root.get(item.getColumn())));
                }
            }
            Order[] orderArr = new Order[orderList.size()];
            return orderList.toArray(orderArr);
        }
    }

    public final static class TimeTool {
        private TimeTool() {
            throw new AssertionError("Tool classes do not allow instantiation");
        }

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

        public static LocalDateTime dayStart(LocalDate time) {
            return dayStart(time.atStartOfDay());
        }

        public static LocalDateTime dayEnd(LocalDate time) {
            return dayEnd(time.atStartOfDay());
        }

        public static LocalDateTime monthStart(LocalDate time) {
            return monthStart(time.atStartOfDay());
        }

        public static LocalDateTime monthEnd(LocalDate time) {
            return monthEnd(time.atStartOfDay());
        }

        public static LocalDateTime yearStart(LocalDate time) {
            return yearStart(time.atStartOfDay());
        }

        public static LocalDateTime yearEnd(LocalDate time) {
            return yearEnd(time.atStartOfDay());
        }

        public static String formatLDT(LocalDateTime time, String pattern) {
            return null == time || EmptyTool.isEmpty(pattern) ? null : DateTimeFormatter.ofPattern(pattern).format(time);
        }

        public static String formatLDT(LocalDateTime time, Pattern pattern) {
            return null == time || null == pattern ? null : DateTimeFormatter.ofPattern(pattern.val()).format(time);
        }

        public static String formatLD(LocalDate time, String pattern) {
            return null == time || EmptyTool.isEmpty(pattern) ? null : DateTimeFormatter.ofPattern(pattern).format(time);
        }

        public static String formatLD(LocalDate time, Pattern pattern) {
            return null == time || null == pattern ? null : DateTimeFormatter.ofPattern(pattern.val()).format(time);
        }

        public static LocalDateTime parseLDT(String timeStr, String pattern) {
            return EmptyTool.isEmpty(timeStr) || EmptyTool.isEmpty(pattern) ?
                    null : LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
        }

        public static LocalDateTime parseLDT(String timeStr, Pattern pattern) {
            return EmptyTool.isEmpty(timeStr) || null == pattern ?
                    null : LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern.val()));
        }

        public static LocalDate parseLD(String timeStr, String pattern) {
            return EmptyTool.isEmpty(timeStr) || EmptyTool.isEmpty(pattern) ?
                    null : LocalDate.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
        }

        public static LocalDate parseLD(String timeStr, Pattern pattern) {
            return EmptyTool.isEmpty(timeStr) || null == pattern ?
                    null : LocalDate.parse(timeStr, DateTimeFormatter.ofPattern(pattern.val()));
        }

        public static LocalDateTime dateToLocal(Date date) {
            return null != date ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        }

        public static Date localToDate(LocalDateTime time) {
            return null != time ? Date.from(time.atZone(ZoneId.systemDefault()).toInstant()) : null;
        }

        public static Long localToTime(LocalDateTime time) {
            return null != time ? time.toInstant(ZoneOffset.of("+8")).toEpochMilli() : null;
        }

        public static Calendar dateToCalendar(Date date) {
            if (null == date) {
                date = new Date();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }

        public static Date addDate(Date date, int calendarType, int num) {
            Calendar calendar = dateToCalendar(date);
            calendar.add(calendarType, num);
            return calendar.getTime();
        }

        public static String formatDate(Date date, String pattern) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }

        public static String formatDate(Date date, Pattern pattern) {
            return formatDate(date, pattern.val());
        }

        public static Date dateStartTime(Date date) {
            Calendar calendar = dateToCalendar(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        public static Date dateEndTime(Date date) {
            Calendar calendar = dateToCalendar(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        }

        public static int monthDaysByDate(Date date) {
            Calendar calendar = dateToCalendar(date);
            calendar.set(Calendar.DATE, 1);
            calendar.roll(Calendar.DATE, -1);
            return calendar.get(Calendar.DATE);
        }
    }

    public final static class IOTool {
        private IOTool() {
            throw new AssertionError("Tool classes do not allow instantiation");
        }

        public static void inToOut(InputStream inStream, OutputStream outStream) {
            byte[] cache = new byte[1024 * 1024 * 20];
            try {
                int len = inStream.read(cache);
                while (len > 0) {
                    outStream.write(cache, 0, len);
                    outStream.flush();
                    len = inStream.read(cache);
                }
                outStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeStream(inStream, outStream);
            }
        }

        public static void closeStream(AutoCloseable... autoCloseables) {
            if (null == autoCloseables) {
                return;
            }
            for (AutoCloseable autoCloseable : autoCloseables) {
                if (null == autoCloseable) {
                    continue;
                }
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final static class EmptyTool {
        private EmptyTool() {
            throw new AssertionError("Tool classes do not allow instantiation");
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

        public static <T> void isNotNull(T obj, Consumer<T> consumer) {
            if (null == obj) {
                return;
            }
            consumer.accept(obj);
        }
    }

    public final static class IdCardTool {
        private IdCardTool() {
            throw new AssertionError("Tool classes do not allow instantiation");
        }

        private static void verifyIdCardStr(String idCardStr) {
            trueThrow(Regex.ID_CARD.verifyFail(idCardStr), new IllegalArgumentException("idCardStr format error"));
        }

        public static Gender getSex(String idCardStr) {
            if (EmptyTool.isEmpty(idCardStr)) {
                return null;
            }
            verifyIdCardStr(idCardStr);
            return Integer.parseInt(idCardStr.substring(16, 17)) % 2 == 0 ? Gender.NV : Gender.NAN;
        }

        public static LocalDate getBirthday(String idCardStr) {
            if (EmptyTool.isEmpty(idCardStr)) {
                return null;
            }
            verifyIdCardStr(idCardStr);
            try {
                return TimeTool.parseLD(idCardStr.substring(6, 14), Pattern.yyyyMMdd);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Integer getCurrAge(String idCardStr) {
            if (EmptyTool.isEmpty(idCardStr)) {
                return null;
            }
            verifyIdCardStr(idCardStr);
            LocalDate birthday;
            try {
                birthday = TimeTool.parseLD(idCardStr.substring(6, 14), Pattern.yyyyMMdd);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return birthdayToCurrAge(birthday);
        }

        public static Integer getAgeByTime(String idCardStr, LocalDate aimTime) {
            if (EmptyTool.isEmpty(idCardStr) || null == aimTime) {
                return null;
            }
            verifyIdCardStr(idCardStr);
            LocalDate birthday;
            try {
                birthday = TimeTool.parseLD(idCardStr.substring(6, 14), Pattern.yyyyMMdd);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return birthdayToAgeByTime(birthday, aimTime);
        }
    }

    public final static class DBTool {
        private DBTool() {
            throw new AssertionError("Tool classes do not allow instantiation");
        }

        public static SaveUpdate getSaveUpdate(DataSource dataSource) {
            return new SaveUpdate(dataSource);
        }

        public final static class SaveUpdate {
            private final DataSource dataSource;

            private SaveUpdate(DataSource dataSource) {
                trueThrow(null == dataSource, new NullPointerException("dataSource must not be null"));
                this.dataSource = dataSource;
            }

            public <T> void saveObjSomeFields(T aim, String... fieldName) {
                saveObjSomeFields(Collections.singletonList(aim), fieldName);
            }

            public <T> void updateSomeFieldsById(T aim, String... fieldName) {
                updateSomeFieldsById(Collections.singletonList(aim), fieldName);
            }

            public <T> void saveObjSomeFields(List<T> aimList, String... fieldName) {
                if (EmptyTool.isEmpty(aimList)) {
                    return;
                }
                Class<?> clazz = aimList.get(0).getClass();
                Table table = clazz.getAnnotation(Table.class);
                trueThrow(null == table, new RuntimeException("实体对象没有javax.persistence.Table注解"));
                String tableName = table.name();
                List<Field> fields = classAllFields(clazz);
                Map<String, Field> fieldMap = ListTool.listToMap(fields, Field::getName, it -> it);
                Map<String, String> fieldColMap = consSaveFieldColMap(fields);
                List<String> srcFields = new ArrayList<>(fieldColMap.keySet());
                List<String> aimFieldList;
                if (null != fieldName && fieldName.length != 0) {
                    List<String> aim = Arrays.asList(fieldName);
                    aimFieldList = srcFields.stream().filter(aim::contains).collect(Collectors.toList());
                } else {
                    aimFieldList = srcFields;
                }
                trueThrow(EmptyTool.isEmpty(aimFieldList), new RuntimeException("要保存的目标字段为空"));
                StringBuilder sb = new StringBuilder("INSERT INTO ");
                StringBuilder sb2 = new StringBuilder();
                sb.append(tableName);
                sb.append("(");
                for (String it : aimFieldList) {
                    sb.append(fieldColMap.get(it)).append(",");
                    sb2.append("?,");
                }
                String str2 = sb2.toString();
                str2 = str2.substring(0, str2.length() - 1);
                String saveStr = sb.toString();
                saveStr = saveStr.substring(0, saveStr.length() - 1);
                saveStr = saveStr + ") VALUES (" + str2 + ")";
                int count = 0;
                int step = 1000;
                Connection connection = null;
                PreparedStatement ps = null;
                try {
                    connection = dataSource.getConnection();
                    connection.setAutoCommit(false);
                    ps = connection.prepareStatement(saveStr);
                    for (T it : aimList) {
                        int idx = 1;
                        for (String item : aimFieldList) {
                            Field colField = fieldMap.get(item);
                            addPsParam(ps, idx, colField, it);
                            idx++;
                        }
                        count++;
                        ps.addBatch();
                        if (count % step == 0) {
                            ps.executeBatch();
                            connection.commit();
                        }
                    }
                    if (aimList.size() % step != 0) {
                        ps.executeBatch();
                        connection.commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("数据保存入库失败");
                } finally {
                    IOTool.closeStream(connection, ps);
                }
            }

            public <T> void updateSomeFieldsById(List<T> aimList, String... fieldName) {
                if (EmptyTool.isEmpty(aimList)) {
                    return;
                }
                Class<?> clazz = aimList.get(0).getClass();
                Table table = clazz.getAnnotation(Table.class);
                trueThrow(null == table, new RuntimeException("实体对象没有javax.persistence.Table注解"));
                String tableName = table.name();
                List<Field> fields = classAllFields(clazz);
                Map<String, Field> fieldMap = ListTool.listToMap(fields, Field::getName, it -> it);
                Map<String, String> fieldColMap = consUpdateFieldColMap(fields);
                String idCol = mapGet(fieldColMap, "id_column");
                trueThrow(null == idCol, new RuntimeException("实体对象没有标注主键javax.persistence.Id注解字段"));
                Set<String> keySet = fieldColMap.keySet();
                List<String> srcFields = new ArrayList<>(keySet);
                srcFields.remove("id_column");
                srcFields.remove("id_field");
                List<String> aimFieldList;
                if (null != fieldName && fieldName.length != 0) {
                    List<String> aim = Arrays.asList(fieldName);
                    aimFieldList = srcFields.stream().filter(aim::contains).collect(Collectors.toList());
                } else {
                    aimFieldList = srcFields;
                }
                trueThrow(EmptyTool.isEmpty(aimFieldList), new RuntimeException("要更新的目标字段为空"));
                StringBuilder sb = new StringBuilder("UPDATE ");
                sb.append(tableName).append(" SET ");
                for (String it : aimFieldList) {
                    sb.append(fieldColMap.get(it)).append("=?,");
                }
                String upStr = sb.toString();
                upStr = upStr.substring(0, upStr.length() - 1);
                upStr = upStr + " WHERE " + idCol + "=?";
                int count = 0;
                int step = 1000;
                Connection connection = null;
                PreparedStatement ps = null;
                try {
                    connection = dataSource.getConnection();
                    connection.setAutoCommit(false);
                    ps = connection.prepareStatement(upStr);
                    for (T it : aimList) {
                        int idx = 1;
                        for (String item : aimFieldList) {
                            Field colField = fieldMap.get(item);
                            addPsParam(ps, idx, colField, it);
                            idx++;
                        }
                        Field idField = fieldMap.get(fieldColMap.get("id_field"));
                        addPsParam(ps, idx, idField, it);
                        count++;
                        ps.addBatch();
                        if (count % step == 0) {
                            ps.executeBatch();
                            connection.commit();
                        }
                    }
                    if (aimList.size() % step != 0) {
                        ps.executeBatch();
                        connection.commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("数据更新入库失败");
                } finally {
                    IOTool.closeStream(connection, ps);
                }
            }

            private Map<String, String> consSaveFieldColMap(List<Field> fields) {
                Map<String, String> map = new HashMap<>();
                for (Field it : fields) {
                    Column column = it.getAnnotation(Column.class);
                    String colName = null != column ? column.name() : getColNameByFieldName(it.getName());
                    map.put(it.getName(), colName);
                }
                return map;
            }

            private Map<String, String> consUpdateFieldColMap(List<Field> fields) {
                Map<String, String> map = new HashMap<>();
                for (Field it : fields) {
                    Column column = it.getAnnotation(Column.class);
                    String colName = null != column ? column.name() : getColNameByFieldName(it.getName());
                    Id id = it.getAnnotation(Id.class);
                    if (null != id) {
                        map.put("id_column", colName);
                        map.put("id_field", it.getName());
                    } else {
                        map.put(it.getName(), colName);
                    }
                }
                return map;
            }

            private String getColNameByFieldName(String fieldName) {
                Matcher matcher = java.util.regex.Pattern.compile("[A-Z]").matcher(fieldName);
                StringBuffer sb = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
                }
                matcher.appendTail(sb);
                return sb.toString();
            }

            private <T> void addPsParam(PreparedStatement ps, int idx, Field colField, T obj) {
                try {
                    colField.setAccessible(true);
                    Object fieldVal = colField.get(obj);
                    if (null == fieldVal) {
                        ps.setNull(idx, JDBCType.NULL.getVendorTypeNumber());
                        colField.setAccessible(false);
                        return;
                    }
                    if (colField.getType().isEnum()) {
                        Enumerated enumerated = colField.getAnnotation(Enumerated.class);
                        EnumType enumType = null == enumerated ? EnumType.ORDINAL : enumerated.value();
                        Enum fieldEnumVal = (Enum) fieldVal;
                        if (EnumType.ORDINAL.equals(enumType)) {
                            ps.setInt(idx, fieldEnumVal.ordinal());
                        } else {
                            ps.setString(idx, fieldEnumVal.name());
                        }
                    } else {
                        ps.setObject(idx, colField.get(obj));
                    }
                    colField.setAccessible(false);
                } catch (SQLException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        public static Select getSelect(EntityManager entityManager) {
            return new Select(entityManager);
        }

        public final static class Select {
            private final static Gson gson = new Gson();

            private final EntityManager entityManager;

            private Select(EntityManager entityManager) {
                trueThrow(null == entityManager, new NullPointerException("entityManager must not be null"));
                this.entityManager = entityManager;
            }

            public <T> List<T> getList(String sql, Class<T> clazz) {
                return getList(sql, null, clazz);
            }

            public <T> T getSingle(String sql, Class<T> clazz) {
                return getSingle(sql, null, clazz);
            }

            public <T> List<T> getList(String sql, Map<String, Object> parameterMap, Class<T> clazz) {
                List resultList = null;
                try {
                    resultList = constructorQuery(sql, parameterMap).getResultList();
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ArrayList<>();
                }
                List<T> result = new ArrayList<>();
                resultList.forEach(it -> {
                    result.add(objMapToObj(it, clazz));
                });
                return result;
            }

            public <T> T getSingle(String sql, Map<String, Object> parameterMap, Class<T> clazz) {
                Object singleResult = null;
                try {
                    singleResult = constructorQuery(sql, parameterMap).getSingleResult();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                if (null == singleResult) {
                    return null;
                }
                return objMapToObj(singleResult, clazz);
            }

            private Query constructorQuery(String sql, Map<String, Object> parameterMap) {
                Query query = entityManager.createNativeQuery(sql)
                        .unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                if (null == parameterMap || parameterMap.isEmpty()) {
                    return query;
                }
                for (String key : parameterMap.keySet()) {
                    query.setParameter(key, parameterMap.get(key));
                }
                return query;
            }

            private <T> T objMapToObj(Object objMap, Class<T> clazz) {
                return gson.fromJson(gson.toJson(objMap), clazz);
            }
        }
    }

    public final static class DESTool {
        private final Key key;
        private final Cipher cipher;

        private DESTool(String secretKey) {
            try {
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                sr.setSeed(secretKey.getBytes());
                KeyGenerator kg = KeyGenerator.getInstance("DES");
                kg.init(sr);
                this.key = kg.generateKey();
                this.cipher = Cipher.getInstance("DES");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("failed to construct encrypt key");
            }
        }

        public static DESTool getInstance(String secretKey) {
            trueThrow(EmptyTool.isEmpty(secretKey), new IllegalArgumentException("secretKey must not be empty"));
            return new DESTool(secretKey);
        }

        public String encode(String aimStr) {
            if (EmptyTool.isEmpty(aimStr)) {
                return null;
            }
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return new String(Base64.getEncoder().encode(cipher.doFinal(aimStr.getBytes(StandardCharsets.UTF_8))));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("failed to encode");
            }
        }

        public String decode(String aimStr) {
            if (EmptyTool.isEmpty(aimStr)) {
                return null;
            }
            try {
                cipher.init(Cipher.DECRYPT_MODE, key);
                return new String(cipher.doFinal(Base64.getDecoder().decode(aimStr)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("failed to decode");
            }
        }
    }
}