package io.uranus.utility.bundle.core.utility.redis.helper;

import io.uranus.utility.bundle.core.utility.redis.extractor.RedisMultiHashExtractor;
import io.uranus.utility.bundle.core.utility.redis.extractor.RedisMultiValueExtractor;
import io.uranus.utility.bundle.core.utility.redis.extractor.RedisSingleHashExtractor;
import io.uranus.utility.bundle.core.utility.redis.extractor.RedisSingleValueExtractor;
import io.uranus.utility.bundle.core.utility.redis.key.RedisKeyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisHelper {

    private final RedisTemplate<String, String> redisTemplate;
    private static RedisKeyManager REDIS_KEY_MANAGER_INSTANCE;

    protected RedisHelper(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        REDIS_KEY_MANAGER_INSTANCE = RedisKeyManagerDelegate.getInstance(redisTemplate);
    }

    /**
     * @param redisTemplate
     * 상위 클래스에서 관리되는 [RedisTemplate], [ObjectMapper]를 주입받아 인스턴스를 생성합니다.
     * 체이닝 지원을 위해 스스로 인스턴스를 생성하고 반환합니다.
     */
    protected static RedisHelper createInstance(RedisTemplate<String, String> redisTemplate) {
        return new RedisHelper(redisTemplate);
    }

    /**
     * RedisKeyManager
     */
    public RedisKeyManager keyManager() {
        return REDIS_KEY_MANAGER_INSTANCE;
    }

    /**
     * Extractors
     */
    public RedisSingleValueExtractor valueExtraction() {
        return RedisSingleValueExtractorDelegate.getInstance(redisTemplate);
    }

    public RedisSingleHashExtractor hashExtraction() {
        return RedisSingleHashExtractorDelegate.getInstance(redisTemplate);
    }

    public RedisMultiValueExtractor multiValueExtraction() {
        return RedisMultiValueExtractorDelegate.getInstance(redisTemplate);
    }

    public RedisMultiHashExtractor multiHashExtraction() {
        return RedisMultiHashExtractorDelegate.getInstance(redisTemplate);
    }

    /**
     * [Redis Value]를 생성합니다.
     * @return succeed or failed.
     */
    public boolean setIntoValue(String key, String val) {
        rejectIfInvalid(key, val);

        try {
            redisTemplate.opsForValue().set(key, val);
            return true;
        } catch (Exception e) {
            printWarningWhilePuttingValue(e);
            return false;
        }
    }

    /**
     * 유효 기간이 존재하는 [Redis Value]를 생성합니다.
     * @return succeed or failed.
     */
    public boolean setIntoValueWithExpiration(String key, String val, Long expiration) {
        rejectIfInvalid(key, val, expiration);

        try {
            redisTemplate.opsForValue().set(key, val, expiration);
            return true;
        } catch (Exception e) {
            printWarningWhilePuttingHash(e);
            return false;
        }
    }

    /**
     * [Redis Hash]를 생성합니다.
     * @return succeed or failed.
     */
    public boolean setIntoHash(String key, String hash, String val) {
        rejectIfInvalid(key, hash, val);

        try {
            redisTemplate.opsForHash().put(key, hash, val);
            return true;
        } catch (Exception e) {
            printWarningWhilePuttingHash(e);
            return false;
        }
    }

    /**
     * @param expiration
     * [Redis Value]의 유효 시간을 [expiration]만큼 연장합니다.
     */
    public void extendsExpiration(String key, Long expiration) {
        rejectIfInvalid(key, expiration);

        Long origin = redisTemplate.getExpire(key, TimeUnit.SECONDS);

        if (origin > 0) {
            redisTemplate.expire(key, origin + expiration, TimeUnit.SECONDS);
        }
    }

    /**
     * @param expiration
     * [Redis value]의 유효 시간을 [expiration]으로 초기화합니다.
     */
    public void resetExpiration(String key, Long expiration) {
        rejectIfInvalid(key, expiration);

        redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
    }

    /**
     * 특정 [key]에 해당하는 데이터를 영속화 시킵니다.
     * @param key
     */
    public void persistValue(String key) {
        rejectIfInvalid(key);

        redisTemplate.persist(key);
    }

    /**
     * @param key
     * [key]에 해당하는 [Redis value] 또는 [Redis hash]를 소멸시킵니다.
     */
    public void invalidate(String key) {
        rejectIfInvalid(key);

        redisTemplate.delete(key);
    }

    /**
     * @param key
     * @param arguments
     * [key] 또는 [arguments]를 검사하고 예외를 발생시킵니다.
     */
    private void rejectIfInvalid(String key, Object... arguments) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        for (Object argument : arguments) {
            if (argument == null) {
                throw new IllegalArgumentException("One or more required arguments for operation are null.");
            }
        }
    }

    /**
     * @param key
     * @param hash
     * @param arguments
     * [key] 또는 [hash] 또는 [arguments]를 검사하고 예외를 발생시킵니다.
     */
    private void rejectIfInvalid(String key, String hash, Object... arguments) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        if (hash == null || hash.isEmpty()) {
            throw new IllegalArgumentException("Hash cannot be null or empty");
        }

        for (Object argument : arguments) {
            if (argument == null) {
                throw new IllegalArgumentException("One or more required arguments for operation are null.");
            }
        }
    }

    /**
     * Exception Warnings
     */
    private void printWarningWhileCasting(Exception e) {
        log.warn("Error parsing object from Redis. If needs more details then trace this [{}]", e.getMessage());
    }

    private void printWarningWhilePuttingValue(Exception e) {
        log.warn("Error putting value into Redis. If needs more details then trace this [{}]", e.getMessage());
    }

    private void printWarningWhilePuttingHash(Exception e) {
        log.warn("Error putting hash into Redis. If needs more details then trace this [{}]", e.getMessage());
    }

    private static class RedisSingleValueExtractorDelegate extends RedisSingleValueExtractor {
        private RedisSingleValueExtractorDelegate(RedisTemplate<String, String> redisTemplate) {
            super(redisTemplate);
        }

        protected static RedisSingleValueExtractor getInstance(RedisTemplate<String, String> redisTemplate) {
            return new RedisSingleValueExtractorDelegate(redisTemplate);
        }
    }

    private static class RedisMultiValueExtractorDelegate extends RedisMultiValueExtractor {
        private RedisMultiValueExtractorDelegate(RedisTemplate<String, String> redisTemplate) {
            super(redisTemplate);
        }

        protected static RedisMultiValueExtractor getInstance(RedisTemplate<String, String> redisTemplate) {
            return new RedisMultiValueExtractorDelegate(redisTemplate);
        }
    }

    private static class RedisSingleHashExtractorDelegate extends RedisSingleHashExtractor {
        private RedisSingleHashExtractorDelegate(RedisTemplate<String, String> redisTemplate) {
            super(redisTemplate);
        }

        protected static RedisSingleHashExtractor getInstance(RedisTemplate<String, String> redisTemplate) {
            return new RedisSingleHashExtractorDelegate(redisTemplate);
        }
    }

    private static class RedisMultiHashExtractorDelegate extends RedisMultiHashExtractor {
        private RedisMultiHashExtractorDelegate(RedisTemplate<String, String> redisTemplate) {
            super(redisTemplate);
        }

        protected static RedisMultiHashExtractor getInstance(RedisTemplate<String, String> redisTemplate) {
            return new RedisMultiHashExtractorDelegate(redisTemplate);
        }
    }

    private static class RedisKeyManagerDelegate extends RedisKeyManager {
        private static RedisKeyManagerDelegate instance;

        private RedisKeyManagerDelegate(RedisTemplate<String, String> redisTemplate) {
            super(redisTemplate);
        }

        protected static synchronized RedisKeyManagerDelegate getInstance(RedisTemplate<String, String> redisTemplate) {
            if (instance == null) {
                instance = new RedisKeyManagerDelegate(redisTemplate);
            }
            return instance;
        }
    }
}
