package io.uranus.utility.bundle.core.utility.redis.generator;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class RedisKeyGenerator {

    private static final String DEFAULT_DELIMITER = "_";

    private String delimiter;
    private String baseKey;
    private List<String> tails = new LinkedList<>();

    protected RedisKeyGenerator() {}

    /**
     * 체이닝 지원을 위해 스스로 인스턴스를 생성하고 반환합니다.
     */
    protected static RedisKeyGenerator createInstance() {
        return new RedisKeyGenerator();
    }

    /**
     * @param delimiter
     * 키를 분리할 구분자를 설정합니다.
     */
    public RedisKeyGenerator withDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    /**
     * 기본 구분자를 사용하도록 설정합니다.
     */
    public RedisKeyGenerator defaultDelimiter() {
        this.delimiter = DEFAULT_DELIMITER;
        return this;
    }

    /**
     * @param baseKey
     * 키의 시작이 되는 [baseKey]를 설정합니다.
     */
    public RedisKeyGenerator baseKey(String baseKey) {
        this.baseKey = baseKey;
        return this;
    }

    /**
     * @param val
     * [key] 생성 간 사용 될 원소를 추가합니다.
     */
    public RedisKeyGenerator add(String val) {
        this.tails.add(val);
        return this;
    }


    /**
     * @throws IllegalArgumentException
     * 각 원소를 검사하고 필수 원소가 존재하지 않을 시 예외를 발생시킵니다.
     * [baseKey]와 [talis] 원소를 조합하여 키를 생성하고 반환합니다.
     */
    public String build() {
        if (this.baseKey == null || this.baseKey.isEmpty()) {
            throw new IllegalArgumentException("baseKey cannot be empty");
        }

        if (this.delimiter == null || this.delimiter.isEmpty()) {
            this.delimiter = DEFAULT_DELIMITER;
        }

        if (this.tails.isEmpty()) {
            return baseKey;
        }

        StringJoiner joiner = new StringJoiner(this.delimiter);
        joiner.add(baseKey);
        this.tails.forEach(joiner::add);

        return joiner.toString();
    }
}
