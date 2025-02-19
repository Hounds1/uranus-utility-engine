package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.utility.dummy.DummyObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JsonSubEngineTest {

    @Test
    void convertToJson() {
        DummyObject object = DummyObject.createObject();

        String val = UranusUtilityEngine.json().writeToJson(object);

        System.out.println(val);
    }

    @Test
    void parseSingleValueJson() {
        String json = convertedDummyObject();

        DummyObject parsed = UranusUtilityEngine.json().parserFor(DummyObject.class)
                .withJson(json)
                .parse();

        DummyObject object = DummyObject.createObject();

        Assertions.assertThat(parsed.getSomeValue()).isEqualTo(object.getSomeValue());
        Assertions.assertThat(parsed.getSomeValue2()).isEqualTo(object.getSomeValue2());
        Assertions.assertThat(parsed.getSomeValue3()).isEqualTo(object.getSomeValue3());
    }

    @Test
    void parseListValueJson() {
        String listJson = convertedListDummyObject();

        List<DummyObject> dummyObjects = UranusUtilityEngine.json().parserFor(DummyObject.class)
                .withJson(listJson)
                .parseToList();

        DummyObject object = DummyObject.createObject();

        for (DummyObject parsed : dummyObjects) {
            Assertions.assertThat(parsed.getSomeValue()).isEqualTo(object.getSomeValue());
            Assertions.assertThat(parsed.getSomeValue2()).isEqualTo(object.getSomeValue2());
            Assertions.assertThat(parsed.getSomeValue3()).isEqualTo(object.getSomeValue3());
        }
    }

    @Test
    void parseMultiValueJson() {
        List<Object> jsonList = multiValueDummyObject();

        List<DummyObject> dummyObjects = UranusUtilityEngine.json().multiParserFor(DummyObject.class)
                .withJsonList(jsonList)
                .parse();

        DummyObject object = DummyObject.createObject();

        for (DummyObject parsed : dummyObjects) {
            Assertions.assertThat(parsed.getSomeValue()).isEqualTo(object.getSomeValue());
            Assertions.assertThat(parsed.getSomeValue2()).isEqualTo(object.getSomeValue2());
            Assertions.assertThat(parsed.getSomeValue3()).isEqualTo(object.getSomeValue3());
        }
    }

    private String convertedDummyObject() {
        return UranusUtilityEngine.json().writeToJson(DummyObject.createObject());
    }

    private String convertedListDummyObject() {
        List<DummyObject> objects = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            objects.add(DummyObject.createObject());
        }

        return UranusUtilityEngine.json().writeToJson(objects);
    }

    private List<Object> multiValueDummyObject() {
        List<Object> objects = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            objects.add(UranusUtilityEngine.json().writeToJson(DummyObject.createObject()));
        }

        return objects;
    }
}
