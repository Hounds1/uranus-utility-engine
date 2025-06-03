package io.uranus.utility.bundle.core.utility.replace.helper;

import io.uranus.utility.bundle.core.utility.replace.helper.replacer.CachingReplacer;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.NoneCachingReplacer;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.ArgumentsInitialPhase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplaceHelper {
    protected ReplaceHelper() {
    }

    protected static ReplaceHelper createInstance() {
        return new ReplaceHelper();
    }

    public ArgumentsInitialPhase replacerFor(String key, String template) {
        CachingReplacerDelegate instance = CachingReplacerDelegate.getInstance();

        instance.withKey(key);
        instance.withTemplate(template);

        return instance;
    }

    public ArgumentsInitialPhase replacerFor(String template) {
        NoneCachingReplacerDelegate instance = NoneCachingReplacerDelegate.getInstance();

        instance.withTemplate(template);

        return instance;
    }

    private static class CachingReplacerDelegate extends CachingReplacer {
        private CachingReplacerDelegate() {
            super();
        }

        protected static CachingReplacerDelegate getInstance() {
            return new CachingReplacerDelegate();
        }
    }

    private static class NoneCachingReplacerDelegate extends NoneCachingReplacer {
        private NoneCachingReplacerDelegate() {
            super();
        }

        protected static NoneCachingReplacerDelegate getInstance() {
            return new NoneCachingReplacerDelegate();
        }
    }
}
