package io.uranus.utility.bundle.core.utility.support.cache;

import com.fasterxml.jackson.core.JsonPointer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonPointerCacheContainer {

    private static final Map<String, JsonPointer> JSON_POINTER_CACHE_CONTAINER = new ConcurrentHashMap<>();
    private static final Map<String, AtomicInteger> NAVIGATE_POINT_INVOCATION_COUNTER = new ConcurrentHashMap<>();
    private static final int HOT_SPOT_THRESHOLD = 10;

    public static JsonPointer computeJsonPointer(String navigatePoint) {
        NAVIGATE_POINT_INVOCATION_COUNTER.computeIfAbsent(navigatePoint, key -> new AtomicInteger(0)).incrementAndGet();

        if (isHotSpot(navigatePoint)) {
            return JSON_POINTER_CACHE_CONTAINER.computeIfAbsent(navigatePoint, JsonPointer::compile);
        }

        return JsonPointer.compile(navigatePoint);
    }

    private static boolean isHotSpot(String navigatePoint) {
        return NAVIGATE_POINT_INVOCATION_COUNTER.get(navigatePoint).get() >= HOT_SPOT_THRESHOLD;
    }

    public static Integer containedSize() {
        return JSON_POINTER_CACHE_CONTAINER.size();
    }

    public static void clear() {
        JSON_POINTER_CACHE_CONTAINER.clear();
    }
}
