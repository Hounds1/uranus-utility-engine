package io.uranus.utility.bundle.core.utility.replace.helper.replacer;

import io.uranus.utility.bundle.core.utility.replace.helper.element.ReplaceableTarget;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.ArgumentsInitialPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.FinalizationPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.replacer.phase.TemplateInitialPhase;
import io.uranus.utility.bundle.core.utility.replace.helper.support.CompiledTemplateCacheableContainer;
import io.uranus.utility.bundle.core.utility.unify.pipeline.PipelineElement;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class NoneCachingReplacer implements TemplateInitialPhase, ArgumentsInitialPhase, FinalizationPhase {

    NoneCachingReplacerPipeline<NoneCachingReplacer> pipeline = new NoneCachingReplacerPipeline<>();
    private String template;
    private Object[] arguments;

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

        if (!StringUtils.hasText(template)) {
            throw new IllegalArgumentException("Template cannot be empty");
        }

        CompiledTemplateCacheableContainer.CompiledTemplate compiledTemplate
                = CompiledTemplateCacheableContainer.disposable(ReplaceableTarget.withoutKey(template));

        return compiledTemplate.format(this.arguments);
    }

    protected static class NoneCachingReplacerPipeline<T> {
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
