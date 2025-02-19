package io.uranus.utility.bundle.core.utility.redis.helper.extractor;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedisMultiValueExtractor {

    private final RedisTemplate<String, String> redisTemplate;
    private Set<String> keys;

    protected RedisMultiValueExtractor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected static RedisMultiValueExtractor createInstance(RedisTemplate<String, String> redisTemplate) {
        return new RedisMultiValueExtractor(redisTemplate);
    }

    /**
     * 추출 과정에 사용할 키 모음을 설정합니다.
     * @param keys
     */
    public RedisMultiValueExtractor withKeys(Set<String> keys) {
        this.keys = keys;
        return this;
    }

    /**
     * 준비된 키 모음을 사용해 값을 추출하고 반환합니다.
     */
    public List<Object> extract() {
        List<Object> values = new ArrayList<>();

        for (String key : keys) {
            Object val = redisTemplate.opsForValue().get(key);

            values.add(val);
        }

        return values;
    }
}
