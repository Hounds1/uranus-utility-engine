package io.uranus.utility.bundle.core.utility.json.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.json.extractor.JsonElementExtractor;
import io.uranus.utility.bundle.core.utility.json.parser.JsonElementParser;

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
     * Instance Delegation
     */
    public JsonElementExtractor elementExtraction() {
        return JsonElementExtractorDelegate.getInstance(objectMapper);
    }

    public <T> JsonElementParser<T> parserFor(Class<T> clazz) {
        return JsonElementParserDelegate.getInstance(objectMapper, clazz);
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

    private static class JsonElementParserDelegate<T> extends JsonElementParser<T> {
        private JsonElementParserDelegate(ObjectMapper objectMapper, Class<T> clazz) {
            super(objectMapper, clazz);
        }

        protected static <T> JsonElementParser<T> getInstance(ObjectMapper objectMapper, Class<T> clazz) {
            return new JsonElementParserDelegate<>(objectMapper, clazz);
        }
    }
}
