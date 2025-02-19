package io.uranus.utility.bundle.core.utility.json.helper.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonMultiElementParser<T> {

    private List<Object> jsonList;
    private final Class<T> castType;
    private final ObjectMapper om;

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

    public List<T> parse() {
        if (this.jsonList == null || this.jsonList.isEmpty()) {
            throw new IllegalArgumentException("jsonList cannot be null or empty");
        }

        List<T> result = new ArrayList<>();

        try {
            for (Object o : jsonList) {
                result.add(om.readValue((String) o, om.getTypeFactory().constructType(castType)));
            }
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json.");
        }

        return result;
    }
}
