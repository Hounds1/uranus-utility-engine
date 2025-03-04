package io.uranus.utility.bundle.core.utility.json.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.json.helper.export.JsonExportProcessor;
import io.uranus.utility.bundle.core.utility.json.helper.extractor.JsonElementExtractor;
import io.uranus.utility.bundle.core.utility.json.helper.parser.JsonMultiElementParser;
import io.uranus.utility.bundle.core.utility.json.helper.parser.JsonSingleElementParser;
import io.uranus.utility.bundle.core.utility.json.helper.recover.JsonRecoveryProcessor;

import java.util.List;

public class JsonHelper {

    protected final ObjectMapper objectMapper;

    protected JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected static JsonHelper getInstance(ObjectMapper objectMapper) {
        return new JsonHelper(objectMapper);
    }

    /**
     * @param val
     * @return Json String
     * 객체를 [Json]으로 변환합니다.
     */
    public String writeToJson(Object val) {
        try {
            return objectMapper.writeValueAsString(val);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while writing to json", e);
        }
    }

    /**
     * 검사 대상이 JSON 포맷이 맞는지 확인합니다.
     * @param val
     * @return boolean
     */
    public boolean isJson(Object val) {
        if (val instanceof String stringTarget) {
            return (stringTarget.startsWith("{") && stringTarget.endsWith("}"))
                    || (stringTarget.startsWith("[") && stringTarget.endsWith("]"));
        }

        return false;
    }

    /**
     * Instance Delegation
     */
    public JsonElementExtractor elementExtraction() {
        return JsonElementExtractorDelegate.getInstance(objectMapper);
    }

    public <T> JsonSingleElementParser<T> parserFor(Class<T> clazz) {
        return JsonSingleElementParserDelegate.getInstance(objectMapper, clazz);
    }

    public <T> JsonMultiElementParser<T> multiParserFor(Class<T> clazz) {
        return JsonMultiElementParserDelegate.getInstance(objectMapper, clazz);
    }

    public <T>JsonRecoveryProcessor<T> recoveryProcessorFor(Class<T> clazz) {
        return JsonRecoveryProcessorDelegate.getInstance(objectMapper, clazz);
    }

    public JsonExportProcessor exportProcessorFor(List<?> objects) {
        return JsonExportProcessorDelegate.getInstance(objects);
    }

    /**
     * Delegators
     */
    private static class JsonElementExtractorDelegate extends JsonElementExtractor {
        private JsonElementExtractorDelegate(ObjectMapper objectMapper) {
            super(objectMapper);
        }

        protected static JsonElementExtractor getInstance(ObjectMapper objectMapper) {
            return new JsonElementExtractorDelegate(objectMapper);
        }
    }

    private static class JsonSingleElementParserDelegate<T> extends JsonSingleElementParser<T> {
        private JsonSingleElementParserDelegate(ObjectMapper objectMapper, Class<T> clazz) {
            super(objectMapper, clazz);
        }

        protected static <T> JsonSingleElementParser<T> getInstance(ObjectMapper objectMapper, Class<T> clazz) {
            return new JsonSingleElementParserDelegate<>(objectMapper, clazz);
        }
    }

    private static class JsonMultiElementParserDelegate<T> extends JsonMultiElementParser<T> {
        private JsonMultiElementParserDelegate(ObjectMapper objectMapper, Class<T> clazz) {
            super(objectMapper, clazz);
        }

        protected static <T> JsonMultiElementParser<T> getInstance(ObjectMapper objectMapper, Class<T> clazz) {
            return new JsonMultiElementParserDelegate<>(objectMapper, clazz);
        }
    }

    private static class JsonRecoveryProcessorDelegate<T> extends JsonRecoveryProcessor<T> {
        private JsonRecoveryProcessorDelegate(ObjectMapper om, Class<T> clazz) {
            super(om, clazz);
        }

        protected static <T> JsonRecoveryProcessor<T> getInstance(ObjectMapper om, Class<T> clazz) {
            return new JsonRecoveryProcessorDelegate<>(om, clazz);
        }
    }

    private static class JsonExportProcessorDelegate extends JsonExportProcessor {
        private JsonExportProcessorDelegate(List<?> objects) {
            super(objects);
        }

        protected static JsonExportProcessor getInstance(List<?> objects) {
            return new JsonExportProcessorDelegate(objects);
        }
    }
}
