package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.redis.converter.RedisHelper;

public class UranusUtilityEngine {

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
    class ChronoUtilityDelegate extends ChronoHelper {
        protected static ChronoHelper getInstance() {
            return ChronoHelper.createInstance();
        }
    }

    class RedisUtilityDelegate extends RedisHelper {
        protected static RedisHelper getInstance() {
            return RedisHelper.createInstance();
        }
    }
}
