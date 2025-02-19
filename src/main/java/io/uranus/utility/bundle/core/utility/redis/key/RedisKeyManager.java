package io.uranus.utility.bundle.core.utility.redis.key;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RedisKeyManager {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String DEFAULT_DELIMITER = "_";
    private static final String ASTERISK = "*";

    protected RedisKeyManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected static RedisKeyManager createInstance(RedisTemplate<String, String> redisTemplate) {
        return new RedisKeyManager(redisTemplate);
    }

    public String generateKey(String baseKey, String... arguments) {
        if (baseKey == null) {
            throw new NullPointerException("baseKey is null");
        }

        StringJoiner joiner = new StringJoiner(DEFAULT_DELIMITER);

        if (baseKey.endsWith(DEFAULT_DELIMITER)) {
            baseKey = baseKey.substring(0, baseKey.length() - 1);
        }

        joiner.add(baseKey);

        for (String argument : arguments) {
            joiner.add(argument);
        }

        return joiner.toString();
    }

    public String generateDelimitedKey(String delimiter, String baseKey, String... arguments) {
        if (baseKey == null) {
            throw new NullPointerException("baseKey is null");
        }

        StringJoiner joiner = new StringJoiner(delimiter == null ? DEFAULT_DELIMITER : delimiter);

        if (baseKey.endsWith(DEFAULT_DELIMITER)) {
            baseKey = baseKey.substring(0, baseKey.length() - 1);
        }

        joiner.add(baseKey);

        for (String argument : arguments) {
            joiner.add(argument);
        }

        return joiner.toString();
    }

    public Set<String> getKeysWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("prefix cannot be null or empty");
        }

        prefix = prefix + ASTERISK;

        return redisTemplate.keys(prefix);
    }

    public Set<String> getHashKeysWithKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key cannot be null or empty");
        }

        Set<Object> hashKeys = redisTemplate.opsForHash().keys(key);

        return hashKeys.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }
}
