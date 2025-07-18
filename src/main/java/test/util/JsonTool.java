package test.util;

import basejpa.util.DSUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public final class JsonTool {
    private JsonTool() {
        throw new AssertionError("Tool classes do not allow instantiation");
    }

    public static <T> String toJson(T aim) {
        return null == aim ? null : JSONUtil.toJsonStr(aim);
    }

    public static <T> T parseObj(String aimStr, Class<T> clazz) {
        return DSUtil.EmptyTool.isEmpty(aimStr) || null == clazz ? null : JSONUtil.toBean(aimStr, clazz);
    }

    public static <T> List<T> parseList(String aimStr, Class<T> clazz) {
        return DSUtil.EmptyTool.isEmpty(aimStr) || null == clazz ?
                new ArrayList<>() : JSONUtil.toList(JSONUtil.parseArray(aimStr), clazz);
    }
}