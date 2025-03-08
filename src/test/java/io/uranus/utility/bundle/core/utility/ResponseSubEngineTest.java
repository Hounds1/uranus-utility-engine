package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObject;
import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObjectResponseCopied;
import io.uranus.utility.bundle.core.utility.response.helper.storage.ResponseTransformCacheContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResponseSubEngineTest {

    @Test
    void responseTransform() {
        DummyObject origin = DummyObject.createObject();

        DummyObjectResponseCopied transformedResponse = UranusUtilityEngine.response().transformerFor(DummyObjectResponseCopied.class)
                .withOrigin(origin)
                .transform();

        DummyObjectResponseCopied transformedResponse2 = UranusUtilityEngine.responseTransform(origin, DummyObjectResponseCopied.class);

        Assertions.assertNotNull(transformedResponse);
        Assertions.assertEquals(origin.getSomeValue(), transformedResponse.getSomeValue());
        Assertions.assertEquals(origin.getSomeValue2(), transformedResponse.getSomeValue2());
        Assertions.assertEquals(origin.getSomeValue3(), transformedResponse.getOtherNameField());

        Assertions.assertEquals(2, ResponseTransformCacheContainer.containedSize());
    }

    @Test
    void responseTransformIgnore() {
        DummyObject origin = DummyObject.createObject();

        DummyObjectResponseCopied transformed = UranusUtilityEngine.response().transformerFor(DummyObjectResponseCopied.class)
                .withOrigin(origin)
                .transform();

        Assertions.assertNotNull(transformed);
        Assertions.assertNull(transformed.getIgnoreField());
    }
}
