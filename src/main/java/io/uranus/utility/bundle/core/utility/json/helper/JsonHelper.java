package io.uranus.utility.bundle.core.utility.json.helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.json.extractor.JsonElementExtractor;
import io.uranus.utility.bundle.core.utility.json.parser.JsonElementParser;

public class JsonHelper<T> {

    protected ObjectMapper objectMapper;

    protected JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    public JsonElementParser<T> elementParsing() {
        return JsonElementParserDelegate.getInstance(objectMapper);
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
        @SuppressWarnings("unchecked")
        private JsonElementParserDelegate(ObjectMapper objectMapper) {
            super(objectMapper);
        }

        protected static <T> JsonElementParser<T> getInstance(ObjectMapper objectMapper) {
            return new JsonElementParserDelegate(objectMapper);
        }
    }
}
