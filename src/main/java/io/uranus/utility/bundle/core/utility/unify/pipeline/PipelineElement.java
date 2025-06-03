package io.uranus.utility.bundle.core.utility.unify.pipeline;

public interface PipelineElement<T> {

    void apply(T element);
}
