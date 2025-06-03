package io.uranus.utility.bundle.core.utility.replace.helper.support;

import io.uranus.utility.bundle.core.utility.replace.helper.element.ReplaceableTarget;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompiledTemplateCacheableContainer {

    private static final Map<String, CompiledTemplate> COMPILED_TEMPLATE_CACHE = new ConcurrentHashMap<>();

    public static boolean containsKey(String key) {
        return COMPILED_TEMPLATE_CACHE.containsKey(key);
    }

    public static CompiledTemplate disposable(ReplaceableTarget rt) {
        return new CompiledTemplate(rt.getTemplate());
    }

    public static CompiledTemplate computeWithSuspect(ReplaceableTarget rt) {
        String key = rt.getKey();
        String target = rt.getTemplate();

        return COMPILED_TEMPLATE_CACHE.compute(key, (k, existing) -> {
            if (existing == null) {
                return new CompiledTemplate(target);
            }
            if (existing.isDiff(target)) {
                throw new IllegalStateException("Compiled template for key [" + key + "] differs.\nExpected: " + existing.origin + "\nActual: " + target);
            }
            return existing;
        });
    }

    public static class CompiledTemplate {
        final String[] segments;
        final int placeholderCount;
        final String origin;

        protected CompiledTemplate(String target) {
            if (target == null) {
                throw new IllegalArgumentException("Template cannot be null");
            }

            this.segments = target.split("\\{\\}");
            this.placeholderCount = PlaceholderCounter.count(target);
            this.origin = target;
        }

        public String format(Object... arguments) {
            if (arguments.length != placeholderCount) {
                throw new IllegalArgumentException("Mismatched argument count");
            }

            StringBuilder builder = new StringBuilder();
            if (segments.length > placeholderCount) {
                int index = 0;
                for (int i = 0; i < placeholderCount; i++) {
                    builder.append(segments[i]).append(arguments[i]);
                    index++;
                }

                for (int i = index; i < segments.length; i++) {
                    builder.append(segments[i]);
                }
            } else {
                for (int i = 0; i < placeholderCount; i++) {
                    builder.append(segments[i]).append(arguments[i]);
                }
            }

            return builder.toString();
        }

        protected boolean isDiff(String s) {
            return !origin.equals(s);
        }
    }

    private static class PlaceholderCounter {
        public static int count(String target) {
            int count = 0;
            for (int i = 0; i < target.length() - 1; i++) {
                if (target.charAt(i) == '{' && target.charAt(i + 1) == '}') {
                    count++;
                    i++;
                }
            }
            return count;
        }
    }
}
