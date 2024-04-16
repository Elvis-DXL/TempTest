package com.elvis.test.query;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DBUtil {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private DataSource dataSource;
    private final static Gson gson = new Gson();

    public <T> List<T> getList(String sql, Class<T> clazz) {
        return this.getList(sql, null, clazz);
    }

    public <T> T getSingle(String sql, Class<T> clazz) {
        return this.getSingle(sql, null, clazz);
    }

    public <T> List<T> getList(String sql, Map<String, Object> parameterMap, Class<T> clazz) {
        List resultList = null;
        try {
            resultList = this.constructorQuery(sql, parameterMap).getResultList();
        } catch (Exception e) {
            log.error("数据查询失败：" + sql + "|参数：" + gson.toJson(parameterMap) + "|异常：" + e.getMessage());
            log.error("数据查询失败", e);
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
            singleResult = this.constructorQuery(sql, parameterMap).getSingleResult();
        } catch (Exception e) {
            log.error("数据查询失败：" + sql + "|参数：" + gson.toJson(parameterMap) + "|异常：" + e.getMessage());
            log.error("数据查询失败", e);
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

    public <T> void updSomeFieldById(T aim, String... fieldName) {
        this.updSomeFieldById(Collections.singletonList(aim), fieldName);
    }

    public <T> void updSomeFieldById(List<T> aimList, String... fieldName) {
        if (null == aimList || aimList.size() == 0) {
            return;
        }
        Class<?> clazz = aimList.get(0).getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (null == table) {
            log.info("实体对象没有Table注解");
            return;
        }
        String tableName = table.name();
        List<Field> fields = allFields(clazz);
        Map<String, Field> fieldMap = fields
                .stream().collect(Collectors.toMap(Field::getName, it -> it, (k1, k2) -> k1));
        Map<String, String> fieldColMap = this.consFieldColMap(fields);
        String idCol = fieldColMap.get("id");
        if (null == idCol) {
            log.info("实体对象没有标注主键ID字段");
            return;
        }
        Set<String> keySet = fieldColMap.keySet();
        List<String> srcFields = new ArrayList<>(keySet);
        srcFields.remove("id");
        srcFields.remove("id_field");
        List<String> aimFieldList = null;
        if (null != fieldName && fieldName.length != 0) {
            List<String> aim = Arrays.asList(fieldName);
            aimFieldList = srcFields.stream().filter(aim::contains).collect(Collectors.toList());
        } else {
            aimFieldList = srcFields;
        }
        if (null == aimFieldList || aimFieldList.size() == 0) {
            log.info("要更新的目标字段为空");
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
        upStr = upStr + " WHERE " + idCol + "=?";
        log.info("执行的SQL【{}】", upStr);
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
                    this.addPsParam(ps, idx, colField, it);
                    idx++;
                }
                Field idField = fieldMap.get(fieldColMap.get("id_field"));
                this.addPsParam(ps, idx, idField, it);
                count++;
                ps.addBatch();
                if (count % step == 0) {
                    ps.executeBatch();
                    connection.commit();
                    log.info("更新入库完成数量：" + count);
                }
            }
            if (aimList.size() % step != 0) {
                ps.executeBatch();
                connection.commit();
            }
            log.info("数据更新入库完成");
        } catch (Exception e) {
            log.error("数据更新入库异常", e);
            throw new IllegalArgumentException("数据更新入库失败");
        } finally {
            closeStream(connection, ps);
        }
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
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> consFieldColMap(List<Field> fields) {
        Map<String, String> map = new HashMap<>();
        for (Field it : fields) {
            Column column = it.getAnnotation(Column.class);
            String colName = null != column ? column.name() : this.getColNameByFieldName(it.getName());
            Id id = it.getAnnotation(Id.class);
            if (null != id) {
                map.put("id", colName);
                map.put("id_field", it.getName());
            } else {
                map.put(it.getName(), colName);
            }
        }
        return map;
    }

    private String getColNameByFieldName(String fieldName) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(fieldName);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
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

    public static void closeStream(AutoCloseable... streams) {
        if (null == streams || streams.length == 0) {
            return;
        }
        for (AutoCloseable item : streams) {
            if (null == item) {
                continue;
            }
            try {
                item.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
