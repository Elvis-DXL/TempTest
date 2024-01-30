package com.elvis.test;


import com.elvis.commons.utils.ClassUtil;
import com.elvis.test.query.DBObj;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {
    public static void main(String[] args) throws Exception {
        updSomeFieldById(Arrays.asList(new DBObj()),"createTime");
    }


    private static <T> void updSomeFieldById(List<T> aimList, String... fieldName) {
        if (null == aimList || aimList.size() == 0) {
            return;
        }
        Class<?> clazz = aimList.get(0).getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (null == table) {
            System.out.println("实体对象没有Table注解");
            return;
        }
        String tableName = table.name();
        List<Field> fields = ClassUtil.allFields(clazz);
        Map<String, Field> fieldMap = fields.stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        Map<String, String> fieldColMap = consFieldColMap(fields);
        String idCol = fieldColMap.get("id");
        if (null == idCol) {
            System.out.println("实体对象没有标注主键ID字段");
            return;
        }
        Set<String> keySet = fieldColMap.keySet();
        keySet.remove("id");
        List<String> aimFieldList = null;
        if (null != fieldName && fieldName.length != 0) {
            List<String> aim = Arrays.asList(fieldName);
            aimFieldList = keySet.stream().filter(aim::contains).collect(Collectors.toList());
        } else {
            aimFieldList = new ArrayList<>(keySet);
        }
        if (null == aimFieldList || aimFieldList.size() == 0) {
            System.out.println("要更新的目标字段为空");
            return;
        }
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(tableName);
        sb.append(" SET ");
        for (String it : aimFieldList) {
            sb.append(fieldColMap.get(it));
            sb.append("=?,");
        }
        String upStr = sb.toString();
        upStr = upStr.substring(0, upStr.length() - 1);
        upStr = upStr + " where " + idCol + "=?";
        System.out.println(upStr);
    }

    private static Map<String, String> consFieldColMap(List<Field> fields) {
        Map<String, String> map = new HashMap<>();
        for (Field it : fields) {
            Column column = it.getAnnotation(Column.class);
            if (null == column) {
                continue;
            }
            String colName = column.name();
            Id id = it.getAnnotation(Id.class);
            if (null != id) {
                map.put("id", colName);
            } else {
                map.put(it.getName(), colName);
            }
        }
        return map;
    }
}