package io.uranus.utility.bundle.core.utility.redis.helper;

import io.uranus.utility.bundle.core.utility.redis.generator.RedisKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisHelper {

    private final RedisTemplate<String, String> redisTemplate;

    private RedisKeyGenerator keyGenerator;
    private RedisKeyGenerator hashGenerator;

    protected RedisHelper(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
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
     * [key]를 만들 준비가 된 [RedisHelper]를 반환합니다.
     * @param baseKey
     * @param arguments
     * @return
     */
    public RedisHelper keygen(String baseKey, String... arguments) {
        this.keyGenerator = this.keygen();

        this.keyGenerator.baseKey(baseKey);

        for (String argument : arguments) {
            this.keyGenerator.add(argument);
        }

        return this;
    }

    /**
     * [delimiter]를 사용해 [key]를 만들 준비가 된 [RedisHelper]를 반환합니다.
     * @param delimiter
     * @param baseKey
     * @param arguments
     * @return
     */
    public RedisHelper keygenWithDelimiter(String delimiter, String baseKey, String... arguments) {
        this.keyGenerator = this.keygen();

        this.keyGenerator.withDelimiter(delimiter);

        this.keyGenerator.baseKey(baseKey);
        for (String argument : arguments) {
            this.keyGenerator.add(argument);
        }

        return this;
    }

    /**
     * [hashKey]를 만들 준비가 된 [RedisHelper]를 반환합니다.
     * @param baseKey
     * @param arguments
     * @return
     */
    public RedisHelper hashGen(String baseKey, String... arguments) {
        this.hashGenerator = this.keygen();

        this.hashGenerator.baseKey(baseKey);

        for (String argument : arguments) {
            this.hashGenerator.add(argument);
        }

        return this;
    }

    /**
     * [delimiter]를 사용해 [hashKey]를 만들 준비가 된 [RedisHelper]를 반환합니다.
     * @param delimiter
     * @param baseKey
     * @param arguments
     * @return
     */
    public RedisHelper hashGenWithDelimiter(String delimiter, String baseKey, String... arguments) {
        this.hashGenerator = this.keygen();

        this.hashGenerator.withDelimiter(delimiter);

        this.hashGenerator.baseKey(baseKey);
        for (String argument : arguments) {
            this.hashGenerator.add(argument);
        }

        return this;
    }

    /**
     * 현재 [keyGenerator]에 저장된 값으로 키를 반환합니다.
     * @return Redis key
     */
    public String getKey() {
        if (this.keyGenerator != null) {
            return this.keyGenerator.build();
        } else {
            return null;
        }
    }

    /**
     * 현재 [hashGenerator]에 저장된 값으로 해쉬키를 반환합니다.
     * @return Redis hash key
     */
    public String getHashKey() {
        if (this.hashGenerator != null) {
            return this.hashGenerator.build();
        } else {
            return null;
        }
    }

    /**
     * [RedisValueOperation]으로부터 값을 탐색해 반환합니다.
     * @return object
     */
    public Object getFromValue(String key) {
        rejectIfInvalid(key);

        return redisTemplate.opsForValue().get(key);
    }

    public Object getFromValue() {
        return redisTemplate.opsForValue().get(this.keyGenerator.build());
    }

    /**
     * [RedisHashOperation]으로부터 값을 탐색해 반환합니다.
     * @return castType Object
     */
    public Object getFromHash(String key, String hash) {
        rejectIfInvalid(key, hash);

        return redisTemplate.opsForHash().get(key, hash);
    }

    public Object getFromHash() {
        return redisTemplate.opsForHash().get(this.keyGenerator.build(), this.hashGenerator.build());
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

    public RedisKeyGenerator hashGen() {
        return RedisKeyGeneratorDelegate.getInstance();
    }

    /**
     * Delegators
     */
    private static class RedisKeyGeneratorDelegate extends RedisKeyGenerator {
        protected static RedisKeyGenerator getInstance() {
            return RedisKeyGenerator.createInstance();
        }
    }
}
