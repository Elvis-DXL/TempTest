package com.elvis.test.enums;

import com.elvis.test.anno.FontTransMethod;
import com.zx.core.tool.utils.JsonUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/8 18:00
 */
public class EnumTest3 {
    public static void main(String[] args) {
        System.out.println(JsonUtil.toJson(enumTrans));
    }

    private static final List<EnumTranPo> enumTrans;

    static {
        //需要翻译的目标枚举类集合
        List<Class<?>> enumClassList = Arrays
                .asList(TypeThree.class);

        //进行反射翻译
        enumTrans = new ArrayList<>();
        for (Class<?> item : enumClassList) {
            if (!item.isEnum()) {
                throw new IllegalArgumentException(item.getName() + " is not Enum");
            }
            FontTransMethod fontTransMethod = item.getAnnotation(FontTransMethod.class);
            if (null == fontTransMethod || null == fontTransMethod.method() || fontTransMethod.method().equals("")) {
                continue;
            }
            List<EnumTranItemPo> itemList = new ArrayList<>();
            try {
                Method fontTran = item.getMethod(fontTransMethod.method());
                Method name = item.getMethod("name");
                for (Object enumItem : item.getEnumConstants()) {
                    itemList.add(new EnumTranItemPo((String) name.invoke(enumItem), (String) fontTran.invoke(enumItem)));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            enumTrans.add(new EnumTranPo(item.getSimpleName(), itemList));
        }
    }
}
