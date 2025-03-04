package io.uranus.utility.bundle.core.utility;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.uranus.utility.bundle.core.global.support.test.dummy.DummyObject;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.ChronoFormat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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

    @Test
    void createRecoveryJson() {
        final String exportPath = "C:\\json-test\\";
        final String defaultFileName = "recovery.json";

        String transform = UranusUtilityEngine.chrono().formatTransform()
                .epochToString()
                .withEpoch(UranusUtilityEngine.chrono().epoch())
                .withFormat(ChronoFormat.JSON_EXPORT_FORMAT_WINDOWS)
                .transform();

        final String addedDateTimePath = exportPath + transform;

        System.out.println(addedDateTimePath);

        List<DummyObject> dummyObjects = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            DummyObject dummyObject = DummyObject.createObject();
            dummyObject.setSomeValue(dummyObject.getSomeValue() + "_" + String.valueOf(i + 1));
            dummyObject.setSomeValue2(dummyObject.getSomeValue2() + "_" + String.valueOf(i + 1));
            dummyObject.setSomeValue3(dummyObject.getSomeValue3() + "_" + String.valueOf(i + 1));

            dummyObjects.add(dummyObject);
        }

        UranusUtilityEngine.json().exportProcessorFor(dummyObjects)
                .withExportPath(addedDateTimePath)
                .withExportFileName(defaultFileName)
                .export();

        File file = new File(addedDateTimePath + File.separator + defaultFileName);

        Assertions.assertThat(file.exists()).isTrue();
    }

    @Test
    void recoveryProcessorTest() {
        final String recoveryPath = "C:\\json-test\\2025\\03\\04\\recovery.json";

        List<DummyObject> recovery = UranusUtilityEngine.json().recoveryProcessorFor(DummyObject.class)
                .withRecoveryPath(recoveryPath)
                .recovery();

        Assertions.assertThat(recovery).isNotNull();
        Assertions.assertThat(recovery.size()).isEqualTo(1000000);
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
