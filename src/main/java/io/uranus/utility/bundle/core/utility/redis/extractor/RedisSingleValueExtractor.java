package io.uranus.utility.bundle.core.utility.redis.extractor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class RedisSingleValueExtractor {

    private final RedisTemplate<String, String> redisTemplate;
    private String key;

    protected RedisSingleValueExtractor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected RedisSingleValueExtractor createInstance(RedisTemplate<String, String> redisTemplate) {
        return new RedisSingleValueExtractor(redisTemplate);
    }

    /**
     * 추출 과정에 사룡할 키를 설정합니다.
     * @param key
     */
    public RedisSingleValueExtractor withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * [RedisValueOperation]으로부터 값을 탐색해 반환합니다.
     * @return object
     */
    public Object extract() {
        if (this.key == null) {
            throw new IllegalStateException("key is null");
        }

        return redisTemplate.opsForValue().get(this.key);
    }
}
