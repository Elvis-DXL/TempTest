package com.elvis.test.enums;

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
public class EnumTest {
    public static void main(String[] args) {
        System.out.println(JsonUtil.toJson(enumTrans));
    }

    private static final List<EnumTranPo> enumTrans;

    static {
        //需要翻译的目标枚举类集合
        List<Class<?>> classList = Arrays
                .asList(TypeOne.class, TypeTwo.class);

        //进行反射翻译
        enumTrans = new ArrayList<>();
        for (Class<?> item : classList) {
            if (!item.isEnum()) {
                throw new IllegalArgumentException(item.getName() + " is not Enum");
            }
            EnumTranPo enumTran = new EnumTranPo();

            String enumName = item.getName().substring(item.getName().lastIndexOf(".") + 1);
            enumTran.setEnumName(enumName);

            List<EnumTranItemPo> itemList = new ArrayList<>();
            enumTran.setItemList(itemList);

            Object[] enumItems = item.getEnumConstants();
            try {
                Method fontTran = item.getMethod("fontTran");
                Method name = item.getMethod("name");
                for (Object enumItem : enumItems) {
                    EnumTranItemPo tranItem = new EnumTranItemPo();
                    itemList.add(tranItem);

                    tranItem.setItemName((String) name.invoke(enumItem));
                    tranItem.setTranStr((String) fontTran.invoke(enumItem));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            enumTrans.add(enumTran);
        }
    }
}
