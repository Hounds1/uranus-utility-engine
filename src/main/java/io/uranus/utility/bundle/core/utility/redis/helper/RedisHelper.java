package io.uranus.utility.bundle.core.utility.redis.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.redis.generator.RedisKeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisHelper {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    protected RedisHelper(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * @param redisTemplate
     * @param objectMapper
     * 상위 클래스에서 관리되는 [RedisTemplate], [ObjectMapper]를 주입받아 인스턴스를 생성합니다.
     * 체이닝 지원을 위해 스스로 인스턴스를 생성하고 반환합니다.
     */
    protected static RedisHelper createInstance(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        return new RedisHelper(redisTemplate, objectMapper);
    }

    /**
     * [RedisValueOperation]으로부터 값을 탐색해 [castType]으로 반환합니다.
     * @return castType Object
     */
    public <T> T castAndGetFromValue(String key, Class<T> castType) {
        rejectIfInvalid(key, castType);

        String val = redisTemplate.opsForValue().get(key);

        if (val == null) {
            return null;
        }

        try {
            return objectMapper.readValue(val, castType);
        } catch (Exception e) {
            printWarningWhileCasting(e);
            return null;
        }
    }

    /**
     * [RedisHashOperation]으로부터 값을 탐색해 [castType]으로 반환합니다.
     * @return castType Object
     */
    public <T> T castAndGetFromHash(String key, String hash, Class<T> castType) {
        rejectIfInvalid(key, hash, castType);

        Object val = redisTemplate.opsForHash().get(key, hash);

        if (val == null) {
            return null;
        }

        try {
            return objectMapper.readValue(String.valueOf(val), castType);
        } catch (Exception e) {
            printWarningWhileCasting(e);
            return null;
        }
    }

    /**
     * [Redis Value]를 생성합니다.
     * @return succeed or failed.
     */
    public boolean setIntoValue(String key, Object val) {
        rejectIfInvalid(key, val);

        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(val));
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
    public boolean setIntoValueWithExpiration(String key, Object val, Long expiration) {
        rejectIfInvalid(key, val, expiration);

        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(val), Duration.ofSeconds(expiration));
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
    public boolean setIntoHash(String key, String hash, Object val) {
        rejectIfInvalid(key, hash, val);

        try {
            redisTemplate.opsForHash().put(key, hash, objectMapper.writeValueAsString(val));
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

    /**
     * Instance Delegation
     */
    public RedisKeyGenerator keygen() {
        return RedisKeyGeneratorDelegate.getInstance();
    }

    /**
     * Delegators
     */
    static class RedisKeyGeneratorDelegate extends RedisKeyGenerator {
        protected static RedisKeyGenerator getInstance() {
            return RedisKeyGenerator.createInstance();
        }
    }
}
