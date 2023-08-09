package newproject.utils;

import newproject.enums.DatePattern;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 静态工具集
 *
 * @author : Elvis
 * @since : 2023/4/11 11:02
 */
public final class StaticUtil {
    //禁止实例化
    private StaticUtil() {
        throw new AssertionError("Utility classes do not allow instantiation");
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

    public static <T> Boolean allFieldNull(T obj) {
        try {
            Class<?> clazz = obj.getClass();
            List<Field> fields = allFields(clazz);
            if (isEmpty(fields)) {
                return Boolean.TRUE;
            }
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                field.setAccessible(false);
                if (null != value) {
                    return Boolean.FALSE;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }

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
        Map<String, Field> srcMap = srcFields.stream().collect(Collectors.toMap(Field::getName, it -> it));
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

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static <T, K> T newByObj(K srcObj, Class<T> clazz) {
        T aimObj = newInstance(clazz);
        copyFields(srcObj, aimObj);
        return aimObj;
    }

    public static <T, K> List<T> listClassChange(List<K> srcList, Class<T> clazz) {
        if (isEmpty(srcList)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (K item : srcList) {
            result.add(newByObj(item, clazz));
        }
        return result;
    }

    public static List<Long> splitToLong(String str, String separatorChars) {
        if (isEmpty(str)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, separatorChars);
        List<Long> result = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            result.add(Long.valueOf(temp));
        }
        return result;
    }

    public static List<Integer> splitToInteger(String str, String separatorChars) {
        if (isEmpty(str)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, separatorChars);
        List<Integer> result = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            result.add(Integer.valueOf(temp));
        }
        return result;
    }

    public static List<String> split(String str, String separatorChars) {
        if (isEmpty(str)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, separatorChars);
        List<String> result = new ArrayList<>();
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }

    public static String joinStr(Collection<String> strCol, String separatorChars) {
        if (isEmpty(strCol)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strCol) {
            sb.append(str);
            sb.append(separatorChars);
        }
        String result = sb.toString();
        return result.substring(0, result.length() - separatorChars.length());
    }

    public static String joinStr(String[] strArr, String separatorChars) {
        if (null == strArr || strArr.length == 0) {
            return null;
        }
        return joinStr(Arrays.asList(strArr), separatorChars);
    }

    public static void closeStream(Closeable... streams) {
        if (null == streams || streams.length == 0) {
            return;
        }
        for (Closeable stream : streams) {
            if (null == stream) {
                continue;
            }
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static Double doubleFmt(Double value, Integer num) {
        if (value == null) {
            return 0D;
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

    public static Date addDate(Date date, int calendarType, int num) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(calendarType, num);
        return calendar.getTime();
    }

    public static String formatDate(Date date, DatePattern datePattern) {
        return formatDate(date, datePattern.val());
    }

    public static Date parseDate(String dateStr, DatePattern datePattern) {
        return parseDate(dateStr, datePattern.val());
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Calendar dateToCalendar(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

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

    public static int monthDaysByDate(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
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

    public static Date dateCurrMonthStartTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, 1);
        return dateStartTime(calendar.getTime());
    }

    public static Date dateCurrMonthEndTime(Date date) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, monthDaysByDate(date));
        return dateEndTime(calendar.getTime());
    }

    public static int countDays(Date startTime, Date endTime) {
        if (null == startTime || null == endTime || startTime.after(endTime)) {
            return 0;
        }
        startTime = dateStartTime(startTime);
        endTime = dateStartTime(addDate(endTime, Calendar.DATE, 1));
        long countDays = (endTime.getTime() - startTime.getTime()) / 1000 / 3600 / 24;
        return (int) countDays;
    }

    public static int dateGet(Date date, int calendarType) {
        return dateToCalendar(date).get(calendarType);
    }

    public static <T> List<T> listDistinct(List<T> srcList) {
        if (null == srcList) {
            return new ArrayList<>();
        }
        return srcList.stream().distinct().collect(Collectors.toList());
    }

    private static final double EARTH_RADIUS = 6371393;//地球平均半径(m)

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

    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace("-", "");
    }

    public static <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> clazz) {
        if (isEmpty(mapList)) {
            return null;
        }
        List<T> result = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = mapList.iterator();
        while (iterator.hasNext()) {
            T data = mapToBean(iterator.next(), clazz);
            if (null == data) {
                continue;
            }
            result.add(data);
        }
        return result;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        Set keySet = map.keySet();
        if (isEmpty(keySet)) {
            return null;
        }
        T data = newInstance(clazz);
        List<Field> fields = allFields(clazz);
        if (isEmpty(fields)) {
            return data;
        }
        for (Field field : fields) {
            String fieldName = field.getName();
            Object value = map.get(fieldName);
            if (null == value) {
                continue;
            }
            field.setAccessible(true);
            try {
                field.set(data, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        return data;
    }

    public static <T> Map<String, Object> beanToMap(T aimObj) {
        List<Field> fields = allFields(aimObj.getClass());
        if (isEmpty(fields)) {
            return new HashMap<>();
        }
        Map<String, Object> result = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(aimObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
            result.put(field.getName(), value);
        }

        return result;
    }

    public static Long max(Long... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Long max = null;
        for (Long number : numbers) {
            if (null == number) {
                continue;
            }
            max = number;
            break;
        }
        if (null == max) {
            return null;
        }
        for (Long number : numbers) {
            if (null == number) {
                continue;
            }
            max = max > number ? max : number;
        }
        return max;
    }

    public static Double max(Double... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Double max = null;
        for (Double number : numbers) {
            if (null == number) {
                continue;
            }
            max = number;
            break;
        }
        if (null == max) {
            return null;
        }
        for (Double number : numbers) {
            if (null == number) {
                continue;
            }
            max = max > number ? max : number;
        }
        return max;
    }

    public static Integer max(Integer... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Integer max = null;
        for (Integer number : numbers) {
            if (null == number) {
                continue;
            }
            max = number;
            break;
        }
        if (null == max) {
            return null;
        }
        for (Integer number : numbers) {
            if (null == number) {
                continue;
            }
            max = max > number ? max : number;
        }
        return max;
    }

    public static Short max(Short... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Short max = null;
        for (Short number : numbers) {
            if (null == number) {
                continue;
            }
            max = number;
            break;
        }
        if (null == max) {
            return null;
        }
        for (Short number : numbers) {
            if (null == number) {
                continue;
            }
            max = max > number ? max : number;
        }
        return max;
    }

    public static Long min(Long... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Long min = null;
        for (Long number : numbers) {
            if (null == number) {
                continue;
            }
            min = number;
            break;
        }
        if (null == min) {
            return null;
        }
        for (Long number : numbers) {
            if (null == number) {
                continue;
            }
            min = min < number ? min : number;
        }
        return min;
    }

    public static Double min(Double... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Double min = null;
        for (Double number : numbers) {
            if (null == number) {
                continue;
            }
            min = number;
            break;
        }
        if (null == min) {
            return null;
        }
        for (Double number : numbers) {
            if (null == number) {
                continue;
            }
            min = min < number ? min : number;
        }
        return min;
    }

    public static Integer min(Integer... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Integer min = null;
        for (Integer number : numbers) {
            if (null == number) {
                continue;
            }
            min = number;
            break;
        }
        if (null == min) {
            return null;
        }
        for (Integer number : numbers) {
            if (null == number) {
                continue;
            }
            min = min < number ? min : number;
        }
        return min;
    }

    public static Short min(Short... numbers) {
        if (null == numbers || numbers.length == 0) {
            return null;
        }
        Short min = null;
        for (Short number : numbers) {
            if (null == number) {
                continue;
            }
            min = number;
            break;
        }
        if (null == min) {
            return null;
        }
        for (Short number : numbers) {
            if (null == number) {
                continue;
            }
            min = min < number ? min : number;
        }
        return min;
    }

    public static Date birthdayByIdCard(String idCard) {
        return parseDate(idCard.substring(6, 14), "yyyyMMdd");
    }

    public static String birthdayStrByIdCard(String idCard, String pattern) {
        return formatDate(birthdayByIdCard(idCard), pattern);
    }

    public static int currAgeByIdCard(String idCard) {
        return dateAgeByIdCard(idCard, new Date());
    }

    public static int dateAgeByIdCard(String idCard, Date date) {
        int bornYear = Integer.parseInt(idCard.substring(6, 10));
        int dateYear = dateGet(date, Calendar.YEAR);
        return Math.max(dateYear - bornYear, 0);
    }

    public static int sexByIdCard(String idCard) {
        int sex = Integer.parseInt(idCard.substring(16, 17));
        return sex % 2;
    }

    public static String sexStrByIdCard(String idCard) {
        return sexByIdCard(idCard) == 1 ? "男" : "女";
    }
}
