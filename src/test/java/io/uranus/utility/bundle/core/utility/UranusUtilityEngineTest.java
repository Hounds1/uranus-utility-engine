package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.json.helper.JsonHelper;
import io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UranusUtilityEngineTest {

    /**
     * Main Engine Test
     */

    @Test
    void chrono() {
        Assertions.assertTrue(UranusUtilityEngine.chrono() != null && UranusUtilityEngine.chrono() instanceof ChronoHelper);
    }

    @Test
    void redis() {
        Assertions.assertTrue(UranusUtilityEngine.redis() != null && UranusUtilityEngine.redis() instanceof RedisHelper);
    }

    @Test
    void json() {
        Assertions.assertTrue(UranusUtilityEngine.json() != null && UranusUtilityEngine.json() instanceof JsonHelper);
    }

    @Test
    void generateRedisKey() {
    }

    @Test
    void generateDelimitedRedisKey() {
    }

    @Test
    void generateRedisHash() {
    }

    @Test
    void generateDelimitedRedisHash() {
    }

    @Test
    void saveJsonToRedis() {
    }

    @Test
    void saveJsonToRedisWithTTL() {
    }

    @Test
    void getFromRedisAs() {
    }

    @Test
    void getHashFromRedisAs() {
    }

    @Test
    void getListFromRedisAs() {
    }

    @Test
    void getSetFromRedisAs() {
    }

    @Test
    void getMapFromRedisAs() {
    }

    @Test
    void getMultiValuesWithPrefixFromRedisAs() {
    }

    @Test
    void getMultiHashWithKeyFromRedisAs() {
    }

    @Test
    void getMultiHashWithPrefixFromRedisAs() {
    }
}