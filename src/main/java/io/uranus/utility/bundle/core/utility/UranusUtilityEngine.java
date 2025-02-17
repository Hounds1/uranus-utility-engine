package io.uranus.utility.bundle.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper;
import org.springframework.data.redis.core.RedisTemplate;

public class UranusUtilityEngine {

    private static final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    private static final ObjectMapper om = new ObjectMapper();

    /**
     * All units cannot be instantiated without the UranusUtilityEngine.
     */

    /**
     * Instance Delegation
     */
    public static ChronoHelper chrono() {
        return ChronoUtilityDelegate.getInstance();
    }

    public static RedisHelper redis() {
        return RedisUtilityDelegate.getInstance();
    }

    /**
     * Delegators
     */
    private static class ChronoUtilityDelegate extends ChronoHelper {
        protected static ChronoHelper getInstance() {
            return ChronoHelper.createInstance();
        }
    }

    private static class RedisUtilityDelegate extends RedisHelper {
        private RedisUtilityDelegate() {
            super(redisTemplate, om);
        }

        protected static RedisHelper getInstance() {
            return new RedisUtilityDelegate();
        }
    }
}
