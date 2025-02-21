package io.uranus.utility.bundle.core.utility.json.helper.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonMultiElementParser<T> {

    private List<Object> jsonList;
    private final Class<T> castType;
    private final ObjectMapper om;

    private boolean strictMode = false;

    protected JsonMultiElementParser(ObjectMapper om, Class<T> castType) {
        this.castType = castType;

        if (castType == null) {
            throw new IllegalArgumentException("castType cannot be null");
        }

        this.om = om;
    }

    protected JsonMultiElementParser<T> createInstance(ObjectMapper om, Class<T> castType) {
        return new JsonMultiElementParser<>(om, castType);
    }

    public JsonMultiElementParser<T> withJsonList(List<Object> jsonList) {
        this.jsonList = jsonList;
        return this;
    }

    public JsonMultiElementParser<T> withStrictMode() {
        strictMode = true;
        return this;
    }

    public List<T> parse() {
        if (this.jsonList == null || this.jsonList.isEmpty()) {
            throw new IllegalArgumentException("jsonList cannot be null or empty");
        }

        List<T> result = new ArrayList<>();
        List<String> failedJsons = new ArrayList<>();

        for (Object o : jsonList) {
            try {
                result.add(om.readValue((String) o, om.getTypeFactory().constructType(castType)));
            } catch (Exception e) {
                failedJsons.add(o.toString());
            }
        }

        if (!failedJsons.isEmpty()) {
            if (strictMode) {
                throw new RuntimeException("Failed to parse JSON elements: " + failedJsons);
            } else {
                log.warn("Some JSON elements failed to parse: {}", failedJsons);
            }
        }

        return result;
    }
}
