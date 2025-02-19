package io.uranus.utility.bundle.core.utility.redis.extractor;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedisMultiHashExtractor {

    private final RedisTemplate<String, String> redisTemplate;
    private String key;

    protected RedisMultiHashExtractor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected static RedisMultiHashExtractor createInstance(RedisTemplate<String, String> redisTemplate) {
        return new RedisMultiHashExtractor(redisTemplate);
    }

    /**
     * 추출 과정에 사용할 키를 설정합니다.
     * @param key
     */
    public RedisMultiHashExtractor withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * 준비된 키가 가진 모든 해쉬를 추출하고 반환합니다.
     */
    public List<Object> extract() {
        if (this.key == null || this.key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        List<Object> result = new ArrayList<>();

        Set<Object> keys = redisTemplate.opsForHash().keys(this.key);
        for (Object k : keys) {
            result.add(redisTemplate.opsForHash().get(this.key, k));
        }

        return result;
    }
}
