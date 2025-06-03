package io.uranus.utility.bundle.core.utility.replace.helper.replacer;

import io.uranus.utility.bundle.core.utility.replace.helper.element.ReplaceableTarget;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.ArgumentsInitialPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.FinalizationPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.KeyInitialPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.TemplateInitialPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.support.CompiledTemplateCacheableContainer;
import io.uranus.utility.bundle.core.utility.unify.pipeline.PipelineElement;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class CachingReplacer implements KeyInitialPhase, TemplateInitialPhase, ArgumentsInitialPhase, FinalizationPhase {

    private final CachingReplacerPipeline<CachingReplacer> pipeline = new CachingReplacerPipeline<>();
    private String key;
    private String template;
    private Object[] arguments;

    @Override
    public TemplateInitialPhase withKey(String key) {
        pipeline.add(c -> c.key = key);

        return this;
    }

    @Override
    public ArgumentsInitialPhase withTemplate(String template) {
        pipeline.add(c -> c.template = template);

        return this;
    }

    @Override
    public FinalizationPhase withArguments(Object... arguments) {
        pipeline.add(c -> c.arguments = arguments);

        return this;
    }

    @Override
    public String replace() {
        pipeline.execute(this);

        if (!StringUtils.hasText(this.key) || !StringUtils.hasText(this.template)) {
            throw new IllegalArgumentException("Key and template are required");
        }

        CompiledTemplateCacheableContainer.CompiledTemplate compiledTemplate
                = CompiledTemplateCacheableContainer.computeWithSuspect(ReplaceableTarget.create(this.key, this.template));

        return compiledTemplate.format(this.arguments);
    }

    protected static class CachingReplacerPipeline<T> {
        private final List<PipelineElement<T>> pipeline = new LinkedList<>();

        public void add(PipelineElement<T> element) {
            pipeline.add(element);
        }

        public void execute(T input) {
            for (PipelineElement<T> item : pipeline) {
                item.apply(input);
            }
        }
    }
}
