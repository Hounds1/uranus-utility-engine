package io.uranus.utility.bundle.core.utility.json.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonElementParser<T> {

    private String json;
    private Class<?> castType;

    private final ObjectMapper om;

    protected JsonElementParser(ObjectMapper om) {
        this.om = om;
    }

    /**
     * @param json
     * 바인딩 대상이 될 [Json]을 지정합니다.
     */
    public JsonElementParser<T> json(String json) {
        this.json = json;
        return this;
    }

    /**
     * @param clazz
     * @param <R>
     * [return type]이 될 클래스를 지정합니다.
     */
    public <R> JsonElementParser<R> castType(Class<R> clazz) {
        JsonElementParser<R> newParser = new JsonElementParser<>(this.om);
        newParser.json = this.json;
        newParser.castType = clazz;
        return newParser;
    }

    /**
     * 단일 객체 파싱
     * - T는 [castType]으로 호출 전 지정
     * ex) ScannerPresenceRedis presenceSet = parser.parse();
     */
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
