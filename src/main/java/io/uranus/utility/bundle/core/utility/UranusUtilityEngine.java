package io.uranus.utility.bundle.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.json.helper.JsonHelper;
import io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper;
import io.uranus.utility.bundle.core.utility.response.helper.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UranusUtilityEngine {

    private static RedisTemplate<String, String> REDIS_TEMPLATE_INSTANCE;
    private static final ObjectMapper OBJECT_MAPPER_INSTANCE = new ObjectMapper();
    private static final JsonHelper JSON_HELPER_INSTANCE;
    private static final ChronoHelper CHRONO_HELPER_INSTANCE;
    private static final RedisHelper REDIS_HELPER_INSTANCE;
    private static final ResponseHelper RESPONSE_HELPER_INSTANCE;

    @Autowired
    private UranusUtilityEngine(RedisTemplate<String, String> redisTemplate) {
        UranusUtilityEngine.REDIS_TEMPLATE_INSTANCE = redisTemplate;
    }

    static {
        JSON_HELPER_INSTANCE = JsonUtilityDelegate.getInstance();
        CHRONO_HELPER_INSTANCE = ChronoUtilityDelegate.getInstance();
        REDIS_HELPER_INSTANCE = RedisUtilityDelegate.getInstance();
        RESPONSE_HELPER_INSTANCE = ResponseUtilityDelegate.getInstance();
    }

    /**
     * All units cannot be instantiated without the UranusUtilityEngine.
     */

    /**
     * Instance Delegation
     */
    public static ChronoHelper chrono() {
        return CHRONO_HELPER_INSTANCE;
    }

    public static RedisHelper redis() {
        return REDIS_HELPER_INSTANCE;
    }

    public static JsonHelper json() {
        return JSON_HELPER_INSTANCE;
    }

    public static ResponseHelper response() {
        return RESPONSE_HELPER_INSTANCE;
    }

    /**
     * 인자에 따라 즉시 키를 생성하여 반환합니다.
     * @param baseKey 기본 키
     * @param arguments 추가 인자
     * @return Redis key
     */
    public static String generateRedisKey(String baseKey, String... arguments) {
        return redis().keyManager().generateKey(baseKey, arguments);
    }

    /**
     * 인자에 따라 즉시 키를 생성하여 반환합니다.
     * 지정한 구분자를 사용합니다.
     * @param baseKey
     * @param arguments
     * @return Redis key
     */
    public static String generateDelimitedRedisKey(String delimiter, String baseKey, String... arguments) {
        return redis().keyManager().generateDelimitedKey(delimiter, baseKey, arguments);
    }

    /**
     * 인자에 따라 즉시 해쉬를 생성하여 반환합니다.
     * @param baseKey
     * @param arguments
     * @return Redis hash
     */
    public static String generateRedisHash(String baseKey, String... arguments) {
        return redis().keyManager().generateKey(baseKey, arguments);
    }

    /**
     * 인자에 따라 즉시 키를 생성하여 반환합니다.
     * 지정한 구분자를 사용합니다.
     * @param baseKey
     * @param arguments
     * @return Redis Hash
     */
    public static String generateDelimitedRedisHash(String delimiter, String baseKey, String... arguments) {
        return redis().keyManager().generateDelimitedKey(delimiter, baseKey, arguments);
    }

    /**
     * 대상 객체를 [Json]으로 변환하고 [redis value]로 생성합니다.
     * @param key
     * @param target
     * @return succeed or failed
     */
    public static boolean saveJsonToRedis(String key, Object target) {
        if (key == null || target == null) {
            throw new IllegalArgumentException("key or target cannot be null.");
        }

        if (json().isJson(target)) {
            return redis().setIntoValue(key, (String) target);
        }

        String json = json().writeToJson(target);
        return redis().setIntoValue(key, json);
    }

    /**
     * 대상 객체를 [Json]으로 변환하고 유효 기간이 [redis value]로 생성합니다.
     * @param key
     * @param target
     * @param expiration
     * @return succeed or failed
     */
    public static boolean saveJsonToRedisWithTTL(String key, Object target, Long expiration) {
        if (key == null || target == null || expiration == null) {
            throw new IllegalArgumentException("key or target or expiration cannot be null.");
        }

        if (json().isJson(target)) {
            return redis().setIntoValueWithExpiration(key, (String) target, expiration);
        }

        String json = json().writeToJson(target);
        return redis().setIntoValueWithExpiration(key, json, expiration);
    }

    /**
     * 대상 객체를 [Json]으로 변환하고 [Redis Hash]로 생성합니다.
     * @param key
     * @param hash
     * @param target
     * @return succeed or failed
     */
    public static boolean saveJsonToRedisHash(String key, String hash, Object target) {
        if (key == null || hash == null || target == null) {
            throw new IllegalArgumentException("key or hash or target cannot be null.");
        }

        if (json().isJson(target)) {
            return redis().setIntoHash(key, hash, (String) target);
        }

        String json = json().writeToJson(target);
        return redis().setIntoHash(key, hash, json);
    }


    /**
     * [Redis Value]를 [castType] 클래스로 맵핑하여 반환합니다.
     * @param key
     * @param castType
     * @return castType Object
     * @param <T>
     */
    public static <T> T getFromRedisAs(String key, Class<T> castType) {
        String val = (String) redis().valueExtraction()
                .withKey(key)
                .extract();

        try {
            return json().parserFor(castType)
                    .withJson(val)
                    .parse();
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to object");
        }
    }

    /**
     * [Redis Hash]를 [castType] 클래스로 맵핑하여 반환합니다.
     * @param key
     * @param castType
     * @return castType Object
     * @param <T>
     */
    public static <T> T getHashFromRedisAs(String key, String hash, Class<T> castType) {
        String val = (String) redis().hashExtraction()
                .withKey(key)
                .withHash(hash)
                .extract();

        try {
            return json().parserFor(castType)
                    .withJson(val)
                    .parse();
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to object.");
        }
    }

    /**
     * [castType]을 원소로 가지는 [List<T>]를 반환합니다.
     * @param key
     * @param castType
     * @return List<T>
     */
    public static <T> List<T> getListFromRedisAs(String key, Class<T> castType) {
        String json = (String) redis().valueExtraction()
                .withKey(key)
                .extract();

        try {
            return json().parserFor(castType)
                    .withJson(json)
                    .parseToList();
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to List.");
        }
    }

    /**
     * [castType]을 원소로 가지는 [Set<T>]를 반환합니다.
     * @param key
     * @param castType
     * @return List<T>
     */
    public static <T> Set<T> getSetFromRedisAs(String key, Class<T> castType) {
        String json = (String) redis().valueExtraction()
                .withKey(key)
                .extract();

        try {
            return json().parserFor(castType)
                    .withJson(json)
                    .parseToSet();
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to List.");
        }
    }

    /**
     * [String]타입의 키와 [castType]을 원소로 가지는 [Map<String, T>]를 반환합니다.
     * @param key
     * @param castType
     * @return List<T>
     */
    public static <T> Map<String, T> getMapFromRedisAs(String key, Class<T> castType) {
        String json = (String) redis().valueExtraction()
                .withKey(key)
                .extract();

        try {
            return json().parserFor(castType)
                    .withJson(json)
                    .parseToMap();
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to List.");
        }
    }

    /**
     * [prefix]를 기반으로 해당하는 모든 키의 값을 파싱 후 반환합니다.
     * @param prefix
     * @param castType
     * @return castType List
     * @param <T>
     */
    public static <T> List<T> getMultiValuesWithPrefixFromRedisAs(String prefix, Class<T> castType, boolean strictMode) {
        Set<String> keys = redis().keyManager().getKeysWithPrefix(prefix);

        List<Object> values = redis().multiValueExtraction()
                .withKeys(keys)
                .extract();

        try {
            if (strictMode) {
                return json().multiParserFor(castType)
                        .withJsonList(values)
                        .withStrictMode()
                        .parse();
            } else {
                return json().multiParserFor(castType)
                        .withJsonList(values)
                        .parse();
            }
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to List.");
        }
    }

    /**
     * [key]를 기반으로 해당 [key]에 속하는 모든 [RedisHash]를 파싱 후 반환합니다.
     * @param key
     * @param castType
     * @return castType List
     * @param <T>
     */
    public static <T> List<T> getMultiHashWithKeyFromRedisAs(String key, Class<T> castType, boolean strictMode) {
        List<Object> extracted = redis().multiHashExtraction()
                .withKey(key)
                .extract();

        try {
            if (strictMode) {
                return json().multiParserFor(castType)
                        .withJsonList(extracted)
                        .withStrictMode()
                        .parse();
            } else {
                return json().multiParserFor(castType)
                        .withJsonList(extracted)
                        .parse();
            }
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to List.");
        }
    }

    /**
     * [prefix] 기반으로 모든 [key]를 조회하고 해당 [key]에 속하는 모든 [hash]를 파싱 후 반환합니다.
     * @param prefix
     * @param castType
     * @return castType List
     * @param <T>
     */
    public static <T> List<T> getMultiHashWithPrefixFromRedisAs(String prefix, Class<T> castType, boolean strictMode) {
        List<Object> fromRedis = new ArrayList<>();

        Set<String> keys = redis().keyManager().getKeysWithPrefix(prefix);
        for (String key : keys) {
            List<Object> extracted = redis().multiHashExtraction()
                    .withKey(key)
                    .extract();

            fromRedis.addAll(extracted);
        }

        try {
            if (strictMode) {
                return json().multiParserFor(castType)
                        .withJsonList(fromRedis)
                        .withStrictMode()
                        .parse();
            } else {
                return json().multiParserFor(castType)
                        .withJsonList(fromRedis)
                        .parse();
            }
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json to List.");
        }
    }

    /**
     * Delegators
     */
    private static class ChronoUtilityDelegate extends ChronoHelper {
        protected static ChronoHelper getInstance() {
            return ChronoHelper.createInstance();
        }
    }

    private static class RedisUtilityDelegate extends RedisHelper {
        private RedisUtilityDelegate() {
            super(REDIS_TEMPLATE_INSTANCE);
        }

        protected static RedisHelper getInstance() {
            return RedisHelper.createInstance(REDIS_TEMPLATE_INSTANCE);
        }
    }

    private static class JsonUtilityDelegate extends JsonHelper {
        private JsonUtilityDelegate() {
            super(OBJECT_MAPPER_INSTANCE);
        }

        protected static JsonHelper getInstance() {
            return JsonHelper.getInstance(OBJECT_MAPPER_INSTANCE);
        }
    }

    private static class ResponseUtilityDelegate extends ResponseHelper {
        private ResponseUtilityDelegate() {
        }

        protected static ResponseHelper getInstance() {
            return ResponseHelper.createInstance();
        }
    }
}
