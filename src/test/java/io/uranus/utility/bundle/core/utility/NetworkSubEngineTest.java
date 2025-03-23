package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObject;
import io.uranus.utility.bundle.core.utility.connection.helper.connector.element.result.ExternalNetworkResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NetworkSubEngineTest {

    @Test
    void isChained() {
        final String someUrl = "https://example.com";
        DummyObject bodyObject = DummyObject.createObject();

        ExternalNetworkResponse response = UranusUtilityEngine.network().open()
                .post()
                .url(someUrl)
                .headers(headers -> {
                    headers.add("Content-Type", "application/json");
                    headers.add("Accept", "application/json");
                })
                .body(UranusUtilityEngine.json().writeToJson(bodyObject))
                .execute();
    }
}
