package io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.base;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;

public abstract class ChronoTransformer<T> {

    protected Region region;

    public ChronoTransformer<T> region(Region region) {
        this.region = region;
        return this;
    }

    public abstract T transform();
}
