package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObject;
import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObjectResponseCopied;
import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.json.helper.JsonHelper;
import io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper;
import io.uranus.utility.bundle.core.utility.response.helper.storage.ResponseTransformCacheContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class UranusUtilityEngineTest {

    private final String BASE_KEY = "MY_BASE_KEY";
    private final String ARG1 = "ARG1";
    private final String ARG2 = "ARG2";
    private final String ARG3 = "ARG3";
    private final String GENERATED_KEY = "MY_BASE_KEY_ARG1_ARG2_ARG3";
    private final String GENERATED_KEY_WITH_DELIMITER = "MY_BASE_KEY:ARG1:ARG2:ARG3";

    /**
     * Main Engine Test
     */

    @Test
    void chrono() {
        Assertions.assertTrue(UranusUtilityEngine.chrono() != null && UranusUtilityEngine.chrono() instanceof ChronoHelper);
    }

    @Test
    void redis() {
        Assertions.assertTrue(UranusUtilityEngine.redis() != null && UranusUtilityEngine.redis() instanceof RedisHelper);
    }

    @Test
    void json() {
        Assertions.assertTrue(UranusUtilityEngine.json() != null && UranusUtilityEngine.json() instanceof JsonHelper);
    }

    @Test
    void generateRedisKey() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);

        Assertions.assertNotNull(key);
        Assertions.assertEquals(GENERATED_KEY, key);
    }

    @Test
    void generateDelimitedRedisKey() {
        String delimitedKey = UranusUtilityEngine.generateDelimitedRedisKey(":", BASE_KEY, ARG1, ARG2, ARG3);

        Assertions.assertNotNull(delimitedKey);
        Assertions.assertEquals(GENERATED_KEY_WITH_DELIMITER, delimitedKey);
    }


    @Test
    void saveJsonToRedis() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);
        DummyObject object = DummyObject.createObject();

        boolean result = UranusUtilityEngine.saveJsonToRedis(key, object);

        Assertions.assertTrue(result);

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void saveJsonToRedisWithTTL() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);
        DummyObject object = DummyObject.createObject();

        boolean result = UranusUtilityEngine.saveJsonToRedisWithTTL(key, object, 1000L);

        Assertions.assertTrue(result);

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getFromRedisAs() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);
        DummyObject object = DummyObject.createObject();

        boolean result = UranusUtilityEngine.saveJsonToRedis(key, object);

        Assertions.assertTrue(result);

        DummyObject fromRedisAs = UranusUtilityEngine.getFromRedisAs(key, DummyObject.class);

        Assertions.assertNotNull(fromRedisAs);
        Assertions.assertEquals(object.getSomeValue(), fromRedisAs.getSomeValue());
        Assertions.assertEquals(object.getSomeValue2(), fromRedisAs.getSomeValue2());
        Assertions.assertEquals(object.getSomeValue3(), fromRedisAs.getSomeValue3());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getHashFromRedisAs() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);
        String hash = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3, "HASH");

        DummyObject object = DummyObject.createObject();

        boolean result = UranusUtilityEngine.saveJsonToRedisHash(key, hash, object);

        Assertions.assertTrue(result);

        DummyObject hashFromRedisAs = UranusUtilityEngine.getHashFromRedisAs(key, hash, DummyObject.class);

        Assertions.assertNotNull(hashFromRedisAs);
        Assertions.assertEquals(object.getSomeValue(), hashFromRedisAs.getSomeValue());
        Assertions.assertEquals(object.getSomeValue2(), hashFromRedisAs.getSomeValue2());
        Assertions.assertEquals(object.getSomeValue3(), hashFromRedisAs.getSomeValue3());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getListFromRedisAs() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);
        List<DummyObject> dummyObjects = listDummyObject();

        boolean result = UranusUtilityEngine.saveJsonToRedis(key, dummyObjects);

        Assertions.assertTrue(result);

        List<DummyObject> listFromRedisAs = UranusUtilityEngine.getListFromRedisAs(key, DummyObject.class);

        Assertions.assertNotNull(listFromRedisAs);
        Assertions.assertEquals(dummyObjects.size(), listFromRedisAs.size());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getSetFromRedisAs() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);
        Set<DummyObject> dummyObjects = setDummyObject();

        boolean result = UranusUtilityEngine.saveJsonToRedis(key, dummyObjects);

        Assertions.assertTrue(result);

        Set<DummyObject> setFromRedisAs = UranusUtilityEngine.getSetFromRedisAs(key, DummyObject.class);

        Assertions.assertNotNull(setFromRedisAs);
        Assertions.assertEquals(dummyObjects.size(), setFromRedisAs.size());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getMapFromRedisAs() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2);
        Map<String, DummyObject> dummyObjects = mapDummyObject();

        boolean result = UranusUtilityEngine.saveJsonToRedis(key, dummyObjects);

        Assertions.assertTrue(result);

        Map<String, DummyObject> mapFromRedisAs = UranusUtilityEngine.getMapFromRedisAs(key, DummyObject.class);

        Assertions.assertNotNull(mapFromRedisAs);
        Assertions.assertEquals(dummyObjects.size(), mapFromRedisAs.size());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getMultiValuesWithPrefixFromRedisAs() {
        List<Object> objects = multiValueDummyObject();

        for (int i = 0; i < objects.size(); i++) {
            String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, String.valueOf(i));

            UranusUtilityEngine.saveJsonToRedis(key, objects.get(i));
        }

        String prefix = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2);
        List<DummyObject> result = UranusUtilityEngine.getMultiValuesWithPrefixFromRedisAs(prefix, DummyObject.class, true);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(objects.size(), result.size());

        for (int i = 0; i < objects.size(); i++) {
            String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, String.valueOf(i));

            UranusUtilityEngine.redis().invalidate(key);
        }
    }

    @Test
    void getMultiHashWithKeyFromRedisAs() {
        String key = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2);
        List<Object> objects = multiValueDummyObject();

        for (int i = 0; i < objects.size(); i++) {
            String hash = UranusUtilityEngine.generateRedisHash(BASE_KEY, ARG1, ARG2, String.valueOf(i));

            UranusUtilityEngine.saveJsonToRedisHash(key, hash, objects.get(i));
        }

        List<DummyObject> result = UranusUtilityEngine.getMultiHashWithKeyFromRedisAs(key, DummyObject.class, true);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(objects.size(), result.size());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void getMultiHashWithPrefixFromRedisAs() {
        String firKey = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2);
        String secKey = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1, ARG2, ARG3);

        List<Object> objects = multiValueDummyObject();

        for (int i = 0; i < objects.size(); i++) {
            String hash = UranusUtilityEngine.generateRedisHash(BASE_KEY, ARG1, ARG2, String.valueOf(i));

            UranusUtilityEngine.saveJsonToRedisHash(firKey, hash, objects.get(i));
            UranusUtilityEngine.saveJsonToRedisHash(secKey, hash, objects.get(i));
        }

        String prefix = UranusUtilityEngine.generateRedisKey(BASE_KEY, ARG1);

        List<DummyObject> result = UranusUtilityEngine.getMultiHashWithPrefixFromRedisAs(prefix, DummyObject.class, true);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(objects.size() * 2, result.size());

        UranusUtilityEngine.redis().invalidate(firKey);
        UranusUtilityEngine.redis().invalidate(secKey);
    }

    @Test
    void responseTransform() {
        DummyObject origin = DummyObject.createObject();

        DummyObjectResponseCopied transformedResponse = UranusUtilityEngine.responseTransform(origin, DummyObjectResponseCopied.class);

        Assertions.assertNotNull(transformedResponse);
        Assertions.assertEquals(origin.getSomeValue(), transformedResponse.getSomeValue());
        Assertions.assertEquals(origin.getSomeValue2(), transformedResponse.getSomeValue2());
        Assertions.assertEquals(origin.getSomeValue3(), transformedResponse.getOtherNameField());

        Assertions.assertEquals(2, ResponseTransformCacheContainer.containedSize());
    }

    private List<DummyObject> listDummyObject() {
        List<DummyObject> objects = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            objects.add(DummyObject.createObject());
        }

        return objects;
    }

    private Set<DummyObject> setDummyObject() {
        Set<DummyObject> objects = new HashSet<>();

        for (int i = 0; i < 20; i++) {
            objects.add(DummyObject.createObject());
        }

        return objects;
    }

    private Map<String, DummyObject> mapDummyObject() {
        Map<String, DummyObject> map = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            map.put(String.valueOf(i), DummyObject.createObject());
        }

        return map;
    }

    private List<Object> multiValueDummyObject() {
        List<Object> objects = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            objects.add(UranusUtilityEngine.json().writeToJson(DummyObject.createObject()));
        }

        return objects;
    }
}