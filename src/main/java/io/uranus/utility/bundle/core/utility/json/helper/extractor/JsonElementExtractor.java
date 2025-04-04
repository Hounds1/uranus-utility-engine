package io.uranus.utility.bundle.core.utility.json.helper.extractor;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.uranus.utility.bundle.core.utility.support.cache.JsonPointerCacheContainer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonElementExtractor {

    private String json;
    private final List<String> targets = new LinkedList<>();

    private final ObjectMapper om;

    protected JsonElementExtractor(ObjectMapper om) {
        this.om = om;
    }

    /**
     * @param json
     * 탐색 대상이 되는 [Json]을 설정합니다.
     */
    public JsonElementExtractor withJson(String json) {
        this.json = json;
        return this;
    }

    /**
     * @param field
     * 탐색 필드를 지정합니다.
     * 추가된 원소의 수에 따라 순차적으로 대상을 탐색합니다.
     */
    public JsonElementExtractor navigate(String field) {
        this.targets.add(field);
        return this;
    }

    /**
     * @return String value of node
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * 노드를 탐색하고 값을 반환합니다.
     * 존재하지 않는 필드일 시 예외를 발생시킵니다.
     */
    public String extract() {
        if (this.json == null || this.json.isEmpty() || this.targets.isEmpty()) {
            throw new IllegalArgumentException("from and target cannot be empty or null.");
        }

        String joinedPoint = String.join("/", targets);
        String navigatePoint = "/" + joinedPoint;

        JsonPointer jsonPointer = JsonPointerCacheContainer.computeJsonPointer(navigatePoint);

        try {
            JsonNode node = om.readTree(json);

            node = node.at(jsonPointer);

            if (node.isMissingNode()) {
                throw new IllegalAccessException("Not exists path [" + navigatePoint + "] in json");
            }

            return node.asText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
