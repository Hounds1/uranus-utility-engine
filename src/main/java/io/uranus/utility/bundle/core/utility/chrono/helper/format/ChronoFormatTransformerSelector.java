package io.uranus.utility.bundle.core.utility.chrono.helper.format;

import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.EpochToStringTransformer;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToEpochTransformer;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToStringTransformer;

public class ChronoFormatTransformerSelector {

    private ChronoFormatTransformerSelector() {}

    public static ChronoFormatTransformerSelector createInstance() {
        return new ChronoFormatTransformerSelector();
    }

    public EpochToStringTransformer epochToString() {
        return EpochToStringTransformer.createInstance();
    }

    public StringToEpochTransformer stringToEpoch() {
        return StringToEpochTransformer.createInstance();
    }

    public StringToStringTransformer stringToString() {
        return StringToStringTransformer.createInstance();
    }
}
