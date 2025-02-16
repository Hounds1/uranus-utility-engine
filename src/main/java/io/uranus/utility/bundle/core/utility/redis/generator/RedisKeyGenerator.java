package io.uranus.utility.bundle.core.utility.redis.generator;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class RedisKeyGenerator {

    private static final String DEFAULT_DELIMITER = "_";

    private String delimiter;
    private String baseKey;
    private List<String> tails = new LinkedList<>();

    protected RedisKeyGenerator() {}

    protected static RedisKeyGenerator createInstance() {
        return new RedisKeyGenerator();
    }

    public RedisKeyGenerator delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public RedisKeyGenerator defaultDelimiter() {
        this.delimiter = DEFAULT_DELIMITER;
        return this;
    }

    public RedisKeyGenerator baseKey(String baseKey) {
        this.baseKey = baseKey;
        return this;
    }

    public RedisKeyGenerator add(String val) {
        this.tails.add(val);
        return this;
    }


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
