package context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

public final class ContextHolder {
    private static final TransmittableThreadLocal<Map<String, Object>> threadLocal = new TransmittableThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = getMap();
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = getMap();
        return map.get(key);
    }

    public static void remove(String key) {
        Map<String, Object> map = getMap();
        map.remove(key);
    }

    private static Map<String, Object> getMap() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map;
    }
}