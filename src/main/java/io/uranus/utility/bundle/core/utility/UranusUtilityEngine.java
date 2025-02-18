package io.uranus.utility.bundle.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.json.helper.JsonHelper;
import io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UranusUtilityEngine {

    private static final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    private static final ObjectMapper om = new ObjectMapper();
    private static final JsonHelper JSON_HELPER_INSTANCE;
    private static final ChronoHelper CHRONO_HELPER_INSTANCE;

    static {
        JSON_HELPER_INSTANCE = JsonUtilityDelegate.getInstance();
        CHRONO_HELPER_INSTANCE = ChronoUtilityDelegate.getInstance();
    }

    /**
     * All units cannot be instantiated without the SaturnUtilityEngine.
     */

    /**
     * Instance Delegation
     */
    public static ChronoHelper chrono() {
        return CHRONO_HELPER_INSTANCE;
    }

    public static RedisHelper redis() {
        return RedisUtilityDelegate.getInstance();
    }

    public static JsonHelper json() {
        return JSON_HELPER_INSTANCE;
    }

    /**
     * 인자에 따라 즉시 키를 생성하여 반환합니다.
     * @param baseKey
     * @param arguments
     * @return Redis key
     */
    public static String generateRedisKey(String baseKey, String... arguments) {
        return redis().keygen(baseKey, arguments).getKey();
    }

    /**
     * 인자에 따라 즉시 키를 생성하여 반환합니다.
     * 지정한 구분자를 사용합니다.
     * @param baseKey
     * @param arguments
     * @return Redis key
     */
    public static String generateDelimitedRedisKey(String delimiter, String baseKey, String... arguments) {
        return redis().keygenWithDelimiter(delimiter, baseKey, arguments).getKey();
    }

    /**
     * 인자에 따라 즉시 해쉬를 생성하여 반환합니다.
     * @param baseKey
     * @param arguments
     * @return Redis hash
     */
    public static String generateRedisHash(String baseKey, String... arguments) {
        return redis().hashGen(baseKey, arguments).getHashKey();
    }

    /**
     * 인자에 따라 즉시 키를 생성하여 반환합니다.
     * 지정한 구분자를 사용합니다.
     * @param baseKey
     * @param arguments
     * @return Redis Hash
     */
    public static String generateDelimitedRedisHash(String delimiter, String baseKey, String... arguments) {
        return redis().hashGenWithDelimiter(delimiter, baseKey, arguments).getHashKey();
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

        if (target instanceof String stringTarget) {

            if ((stringTarget.startsWith("{") && stringTarget.endsWith("}"))
                    || (stringTarget.startsWith("[") && stringTarget.endsWith("]"))) {
                return redis().setIntoValue(key, stringTarget);
            }
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

        if (target instanceof String stringTarget) {

            if ((stringTarget.startsWith("{") && stringTarget.endsWith("}"))
                    || (stringTarget.startsWith("[") && stringTarget.endsWith("]"))) {
                return redis().setIntoValueWithExpiration(key, stringTarget, expiration);
            }
        }

        String json = json().writeToJson(target);
        return redis().setIntoValueWithExpiration(key, json, expiration);
    }

    /**
     * [Redis Value]를 [castType] 클래스로 맵핑하여 반환합니다.
     * @param key
     * @param castType
     * @return castType Object
     * @param <T>
     */
    public static <T> T getFromRedisAs(String key, Class<T> castType) {
        String val = (String) redis().getFromValue(key);

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
        String val = (String) redis().getFromHash(key, hash);

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
        String json = (String) redis().getFromValue(key);

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
        String json = (String) redis().getFromValue(key);

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
        String json = (String) redis().getFromValue(key);

        try {
            return json().parserFor(castType)
                    .withJson(json)
                    .parseToMap();
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
            super(redisTemplate);
        }

        protected static RedisHelper getInstance() {
            return new RedisUtilityDelegate();
        }
    }

    private static class JsonUtilityDelegate extends JsonHelper {
        private JsonUtilityDelegate() {
            super(om);
        }

        protected static JsonHelper getInstance() {
            return JsonHelper.getInstance(om);
        }
    }
}
