package io.uranus.utility.bundle.core.utility.chrono.helper.format;

import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.EpochToStringTransformer;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToEpochTransformer;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToStringTransformer;

public class ChronoFormatTransformerSelector {

    protected ChronoFormatTransformerSelector() {}

    protected static ChronoFormatTransformerSelector createInstance() {
        return new ChronoFormatTransformerSelector();
    }

    /**
     * @return EpochToStringTransformer
     * Long => String 변환 체인을 반환합니다.
     */
    public EpochToStringTransformer epochToString() {
        return EpochToStringTransformerDelegate.getInstance();
    }

    /**
     * @return StringToEpochTransformer
     * String => Long 변환 체인을 반환합니다.
     */
    public StringToEpochTransformer stringToEpoch() {
        return StringToEpochTransformerDelegate.getInstance();
    }

    /**
     * @return StringToStringTransformer
     * String => String 변환 체인을 반환합니다.
     */
    public StringToStringTransformer stringToString() {
        return StringToStringTransformerDelegate.getInstance();
    }

    /**
     * Delegators
     */
    private static class EpochToStringTransformerDelegate extends EpochToStringTransformer {
        protected static EpochToStringTransformer getInstance() {
            return EpochToStringTransformer.createInstance();
        }
    }

    private static class StringToEpochTransformerDelegate extends StringToEpochTransformer {
        protected static StringToEpochTransformer getInstance() {
            return StringToEpochTransformer.createInstance();
        }
    }

    private static class StringToStringTransformerDelegate extends StringToStringTransformer {
        protected static StringToStringTransformer getInstance() {
            return StringToStringTransformer.createInstance();
        }
    }
}
