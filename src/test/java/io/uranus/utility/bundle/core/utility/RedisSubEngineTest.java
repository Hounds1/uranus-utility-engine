package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObject;
import io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisSubEngineTest {

    private final String BASE_KEY = "MY_BASE_KEY";
    private final String ARG1 = "ARG1";
    private final String ARG2 = "ARG2";
    private final String ARG3 = "ARG3";

    @Test
    void generateKey() {
        String key = UranusUtilityEngine.redis().keyManager().generateKey(BASE_KEY, ARG1, ARG2, ARG3);

        System.out.println(key);
    }

    @Test
    void setIntoValue() {
        DummyObject dummy = DummyObject.createObject();
        String key = keygen();

        String json = UranusUtilityEngine.json().writeToJson(dummy);

        boolean result = UranusUtilityEngine.redis().setIntoValue(key, json);

        Assertions.assertTrue(result);

        Object extract = UranusUtilityEngine.redis().valueExtraction()
                .withKey(key)
                .extract();

        DummyObject parsed = UranusUtilityEngine.json().parserFor(DummyObject.class)
                .withJson(String.valueOf(extract))
                .parse();

        Assertions.assertInstanceOf(DummyObject.class, parsed);
        Assertions.assertEquals(dummy.getSomeValue(), parsed.getSomeValue());
        Assertions.assertEquals(dummy.getSomeValue2(), parsed.getSomeValue2());
        Assertions.assertEquals(dummy.getSomeValue3(), parsed.getSomeValue3());

        UranusUtilityEngine.redis().invalidate(key);
    }

    @Test
    void setIntoHash() {
        DummyObject dummy = DummyObject.createObject();

        String key = keygen();
        String hash = dummyHash();
        String json = UranusUtilityEngine.json().writeToJson(dummy);

        boolean result = UranusUtilityEngine.redis().setIntoHash(key, hash, json);

        Assertions.assertTrue(result);

        Object extract = UranusUtilityEngine.redis().hashExtraction()
                .withKey(key)
                .withHash(hash)
                .extract();

        DummyObject parsed = UranusUtilityEngine.json().parserFor(DummyObject.class)
                .withJson(String.valueOf(extract))
                .parse();

        Assertions.assertInstanceOf(DummyObject.class, parsed);
        Assertions.assertEquals(dummy.getSomeValue(), parsed.getSomeValue());
        Assertions.assertEquals(dummy.getSomeValue2(), parsed.getSomeValue2());
        Assertions.assertEquals(dummy.getSomeValue3(), parsed.getSomeValue3());

        UranusUtilityEngine.redis().invalidate(key);
    }

    private String keygen() {
        return UranusUtilityEngine.redis().keyManager().generateKey(BASE_KEY, ARG1, ARG2, ARG3);
    }

    private String dummyHash() {
        return UranusUtilityEngine.redis().keyManager().generateKey(BASE_KEY, ARG1, ARG2, ARG3, "DUMMY", "HASH");
    }
}
