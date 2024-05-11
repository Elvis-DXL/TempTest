package com.elvis.test.query;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DBUtil {
    private final static Gson gson = new Gson();
    @Autowired
    private EntityManager entityManager;

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
}
