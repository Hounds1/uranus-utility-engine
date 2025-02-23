package io.uranus.utility.bundle.core.utility.response.helper.storage;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ResponseTransformCacheContainer {

    private static final Map<Class<?>, Map<String, Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();

    private ResponseTransformCacheContainer() {}

    public static Map<String, Field> getClassFields(Class<?> clazz) {
        return CLASS_FIELD_CACHE.computeIfAbsent(clazz, key -> {
            Map<String, Field> fieldMap = new HashMap<>();
            for (Field field : key.getDeclaredFields()) {
                field.setAccessible(true);
                fieldMap.put(field.getName(), field);
            }
            return fieldMap;
        });
    }

    public static boolean isContainedClass(Class<?> clazz) {
        return CLASS_FIELD_CACHE.containsKey(clazz);
    }

    public static int containedSize() {
        return CLASS_FIELD_CACHE.size();
    }
}

