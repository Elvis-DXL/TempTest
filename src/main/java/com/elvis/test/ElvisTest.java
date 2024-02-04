package com.elvis.test;


import com.elvis.commons.utils.ClassUtil;
import com.elvis.test.excel.ExcelPojoOne;
import com.elvis.test.excel.ExcelPojoTwo;
import com.zx.core.tool.utils.JsonUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {
    public static void main(String[] args) throws Exception {
        ExcelPojoOne obj1 = new ExcelPojoOne();
        obj1.setName("111");
        obj1.setAge(123);

        ExcelPojoTwo obj2 = copySomeField(obj1, new ExcelPojoTwo(), "name","age");
        System.out.println(JsonUtil.toJson(obj2));
    }

    public static <T, K> K copySomeField(T src, K aim, String... fields) {
        List<Field> srcFields = ClassUtil.allFields(src.getClass());
        List<Field> aimFields = ClassUtil.allFields(aim.getClass());
        Map<String, Field> srcMap = srcFields
                .stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        Map<String, Field> aimMap = aimFields
                .stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        List<String> fieldList = Arrays.asList(fields);
        if (null == fieldList || fieldList.size() == 0) {
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
}