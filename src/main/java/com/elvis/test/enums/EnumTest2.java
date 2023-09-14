package com.elvis.test.enums;

import com.zx.core.tool.utils.JsonUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/8 18:00
 */
public class EnumTest2 {
    public static void main(String[] args) {
        System.out.println(JsonUtil.toJson(enumTrans));
    }

    private static final Map<String, Map<String, String>> enumTrans;

    static {
        //需要翻译的目标枚举类集合
        List<Class<?>> classList = Arrays
                .asList(TypeOne.class, TypeTwo.class);

        //进行反射翻译
        enumTrans = new HashMap<>();
        for (Class<?> item : classList) {
            if (!item.isEnum()) {
                throw new IllegalArgumentException(item.getName() + " is not Enum");
            }

            Map<String, String> itemMap = new HashMap<>();
            try {
                Method fontTran = item.getMethod("fontTran");
                Method name = item.getMethod("name");
                for (Object enumItem : item.getEnumConstants()) {
                    itemMap.put((String) name.invoke(enumItem), (String) fontTran.invoke(enumItem));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            enumTrans.put(item.getSimpleName(), itemMap);
        }
    }
}
