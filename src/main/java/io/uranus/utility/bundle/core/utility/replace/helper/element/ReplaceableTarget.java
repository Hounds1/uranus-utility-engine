package io.uranus.utility.bundle.core.utility.replace.helper.element;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplaceableTarget {

    private final String key;
    private final String template;

    ReplaceableTarget() {
        throw new IllegalArgumentException("Cannot create a replaceable target");
    }

    public static ReplaceableTarget create(String key, String template) {
        return new ReplaceableTarget(key, template);
    }

    public static ReplaceableTarget withoutKey(String template) {
        return new ReplaceableTarget(null, template);
    }
}
