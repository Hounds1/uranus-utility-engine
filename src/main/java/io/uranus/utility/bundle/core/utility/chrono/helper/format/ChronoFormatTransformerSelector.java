package io.uranus.utility.bundle.core.utility.chrono.helper.format;

import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.EpochToStringTransformer;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToEpochTransformer;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToStringTransformer;

public class ChronoFormatTransformerSelector {

    protected ChronoFormatTransformerSelector() {}

    protected static ChronoFormatTransformerSelector createInstance() {
        return new ChronoFormatTransformerSelector();
    }

    public EpochToStringTransformer epochToString() {
        return EpochToStringTransformerDelegate.getInstance();
    }

    public StringToEpochTransformer stringToEpoch() {
        return StringToEpochTransformerDelegate.getInstance();
    }

    public StringToStringTransformer stringToString() {
        return StringToStringTransformerDelegate.getInstance();
    }

    static class EpochToStringTransformerDelegate extends EpochToStringTransformer {
        protected static EpochToStringTransformer getInstance() {
            return EpochToStringTransformer.createInstance();
        }
    }

    static class StringToEpochTransformerDelegate extends StringToEpochTransformer {
        protected static StringToEpochTransformer getInstance() {
            return StringToEpochTransformer.createInstance();
        }
    }

    static class StringToStringTransformerDelegate extends StringToStringTransformer {
        protected static StringToStringTransformer getInstance() {
            return StringToStringTransformer.createInstance();
        }
    }
}
