package io.uranus.utility.bundle.core.redis.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SimpleRedisObjectConverter {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


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

    public void extendsExpiration(String key, Long expiration) {
        rejectIfInvalid(key, expiration);

        Long origin = redisTemplate.getExpire(key, TimeUnit.SECONDS);

        if (origin > 0) {
            redisTemplate.expire(key, origin + expiration, TimeUnit.SECONDS);
        }
    }

    public void resetExpiration(String key, Long expiration) {
        rejectIfInvalid(key, expiration);

        redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
    }

    public void invalidate(String key) {
        rejectIfInvalid(key);

        redisTemplate.delete(key);
    }

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

    private void printWarningWhileCasting(Exception e) {
        log.warn("Error parsing object from Redis. If needs more details then trace this [{}]", e.getMessage());
    }

    private void printWarningWhilePuttingValue(Exception e) {
        log.warn("Error putting value into Redis. If needs more details then trace this [{}]", e.getMessage());
    }

    private void printWarningWhilePuttingHash(Exception e) {
        log.warn("Error putting hash into Redis. If needs more details then trace this [{}]", e.getMessage());
    }
}
