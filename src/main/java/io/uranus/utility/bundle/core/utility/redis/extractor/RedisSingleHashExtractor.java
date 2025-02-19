package io.uranus.utility.bundle.core.utility.redis.extractor;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisSingleHashExtractor {

    private final RedisTemplate<String, String> redisTemplate;
    private String key;
    private String hash;

    protected RedisSingleHashExtractor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected static RedisSingleHashExtractor createInstance(RedisTemplate<String, String> redisTemplate) {
        return new RedisSingleHashExtractor(redisTemplate);
    }

    /**
     * 추출 과정에 사용할 키를 설정합니다.
     */
    public RedisSingleHashExtractor withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * 추출 과정에 사용할 해쉬키를 설정합니다.
     * @param hash
     */
    public RedisSingleHashExtractor withHash(String hash) {
        this.hash = hash;
        return this;
    }

    /**
     * [RedisHashOperation]으로부터 값을 탐색해 반환합니다.
     * @return castType Object
     */
    public Object extract() {
        if (this.key == null || this.key.isEmpty() || this.hash == null || this.hash.isEmpty()) {
            throw new IllegalArgumentException("Key and hash cannot be null or empty");
        }

        return redisTemplate.opsForHash().get(this.key, this.hash);
    }
}
