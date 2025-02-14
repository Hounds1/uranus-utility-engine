package io.uranus.utility.bundle.core.redis.builder;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.StringJoiner;

public class RedisKeyGenerator {

    private static final String DEFAULT_DELIMITER = "_";

    public static String build(@Nonnull final String baseKey, @Nullable final String delimiter, final String... arguments) {
        if (baseKey.isEmpty()) {
            throw new IllegalArgumentException("baseKey cannot be empty");
        }

        if (arguments == null || arguments.length == 0) {
            return baseKey;
        }

        StringJoiner joiner = new StringJoiner(Optional.ofNullable(delimiter).orElse(DEFAULT_DELIMITER));

        joiner.add(baseKey);

        for (String argument : arguments) {
            if (argument != null) {
                joiner.add(argument);
            }
        }

        return joiner.toString();
    }
}
