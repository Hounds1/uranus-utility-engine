package io.uranus.utility.bundle.core.utility.json.helper.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonSingleElementParser<T> {

    private String json;
    private final Class<T> castType;

    private final ObjectMapper om;

    protected JsonSingleElementParser(ObjectMapper om, Class<T> castType) {
        this.om = om;

        if (castType == null) {
            throw new IllegalArgumentException("castType cannot be null");
        }
        this.castType = castType;
    }

    protected JsonSingleElementParser<T> createInstance(ObjectMapper om, Class<T> castType) {
        return new JsonSingleElementParser<>(om, castType);
    }

    /**
     * @param json
     * 바인딩 대상이 될 [Json]을 지정합니다.
     */
    public JsonSingleElementParser<T> withJson(String json) {
        this.json = json;
        return this;
    }

    /**
     * 단일 객체 파싱
     * - T는 [castType]으로 호출 전 지정
     * ex) ScannerPresenceRedis presenceSet = parser.parse();
     */
    public T parse() {
        if (json == null) {
            throw new IllegalArgumentException("Json value cannot be null.");
        }

        try {
            return om.readValue(this.json, om.getTypeFactory().constructType(castType));
        } catch (Exception e) {
            throw new RuntimeException("An error has been occurred while parsing json.");
        }
    }

    /**
     * List<T> 형태로 파싱
     * - T는 [castType]으로 호출 전 지정
     * ex) List<ScannerPresenceRedis> presenceList = parser.parseToList();
     */
    public List<T> parseToList() {
        if (json == null) {
            throw new IllegalArgumentException("Json value cannot be null.");
        }
        if (castType == null) {
            throw new IllegalArgumentException("castType cannot be null.");
        }

        try {
            return om.readValue(
                    json,
                    om.getTypeFactory().constructCollectionType(List.class, castType)
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while parsing JSON to List.");
        }
    }

    /**
     * Set<T> 형태로 파싱
     * - T는 [castType]으로 호출 전 지정
     * ex) Set<ScannerPresenceRedis> presenceSet = parser.parseToSet();
     */
    public Set<T> parseToSet() {
        if (json == null) {
            throw new IllegalArgumentException("Json value cannot be null.");
        }
        if (castType == null) {
            throw new IllegalArgumentException("castType cannot be null.");
        }

        try {
            return om.readValue(json, om.getTypeFactory().constructCollectionType(Set.class, castType)
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while parsing JSON to Set.");
        }
    }

    /**
     * Map<String, T> 형태로 파싱
     * - T는 [castType]으로 호출 전 지정
     * ex) Map<String, ScannerPresenceRedis> map = parser.parseToMap();
     */
    public Map<String, T> parseToMap() {
        if (json == null) {
            throw new IllegalArgumentException("Json value cannot be null.");
        }
        if (castType == null) {
            throw new IllegalArgumentException("castType cannot be null.");
        }

        try {
            return om.readValue(json, om.getTypeFactory().constructMapType(Map.class, String.class, castType)
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while parsing JSON to Map.");
        }
    }
}
