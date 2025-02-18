package io.uranus.utility.bundle.core.utility.chrono.helper;

import io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator;
import io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateTimeCalculator;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.ChronoFormatTransformerSelector;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ChronoHelper {

    private static final Region DEFAULT_REGION = Region.REPUBLIC_OF_KOREA;

    protected ChronoHelper() {}

    /**
     * @return ChronoHelper
     * Create and return self
     */
    protected static ChronoHelper createInstance() {
        return new ChronoHelper();
    }

    /**
     * @return LocalDateTime of now with default region
     */
    public LocalDateTime now() {
        return now(DEFAULT_REGION);
    }

    /**
     * @param region
     * @return LocalDateTime of now with region
     */
    public LocalDateTime now(@Nullable Region region) {
        return LocalDateTime.now().atZone(ZoneId.of(withDefaultIfNecessary(region).getTimeZone())).toLocalDateTime();
    }

    /**
     * @return Epoch with default region
     */
    public Long epoch() {
        return epoch(DEFAULT_REGION);
    }

    /**
     * @param region
     * @return Epoch with region
     */
    public Long epoch(@Nullable Region region) {
        return ZonedDateTime.now(ZoneId.of(withDefaultIfNecessary(region).getTimeZone())).toEpochSecond();
    }

    /**
     * Provide LocalDateTime compute chain
     * @see LocalDateTimeCalculator
     * @return ChronoCalculator
     */
    public LocalDateTimeCalculator dateTimeComputeChain() {
        return dateTimeComputeChain(now(DEFAULT_REGION), DEFAULT_REGION);
    }

    /**
     * Provide LocalDateTime compute chain
     * @see LocalDateTimeCalculator
     * @param region
     * @return LocalDateTimeCalculator
     */
    public LocalDateTimeCalculator dateTimeComputeChain(@Nullable Region region) {
        return dateTimeComputeChain(now(withDefaultIfNecessary(region)), region);
    }

    /**
     * provide LocalDate compute chain
     * @see LocalDateCalculator
     * @return LocalDateCalculator
     */
    public LocalDateCalculator dateComputeChain() {
        return dateComputeChain(ZonedDateTime.now(ZoneId.of(DEFAULT_REGION.getTimeZone())));
    }

    /**
     * provide LocalDate compute chain
     * @see LocalDateCalculator
     * @return LocalDateCalculator
     */
    public LocalDateCalculator dateComputeChain(@Nullable Region region) {
        return dateComputeChain(ZonedDateTime.now(ZoneId.of(withDefaultIfNecessary(region).getTimeZone())));
    }

    /**
     * return default region if parameter is null
     * @return default region
     */
    private static Region withDefaultIfNecessary(@Nullable Region region) {
        if (region != null) {
            return region;
        }

        return DEFAULT_REGION;
    }

    /**
     * Instance Delegation
     */

    /**
     * invoke constructor of LocalDateTimeCalculator
     * @see LocalDateTimeCalculator
     * @param localDateTime
     * @return LocalDateTimeCalculator
     */
    public LocalDateTimeCalculator dateTimeComputeChain(@Nonnull LocalDateTime localDateTime, Region region) {
        return LocalDateTimeCalculatorDelegate.getInstance(localDateTime, region);
    }

    /**
     * invoke constructor of LocalDateCalculator
     * @see LocalDateCalculator
     * @param zonedDateTime
     * @return LocalDateCalculator
     */

    public LocalDateCalculator dateComputeChain(@Nonnull ZonedDateTime zonedDateTime) {
        return LocalDateCalculatorDelegate.getInstance(zonedDateTime);
    }

    /**
     * Delegate ChronoFormatTransformerSelector for support chaining computation.
     * @see ChronoFormatTransformerSelector
     * @return ChronoFormatTransformerSelector
     */
    public ChronoFormatTransformerSelector formatTransform() {
        return ChronoFormatTransformerSelectorDelegate.getInstance();
    }

    /**
     * Delegators
     */
    static class LocalDateTimeCalculatorDelegate extends LocalDateTimeCalculator {
        protected static LocalDateTimeCalculator getInstance(LocalDateTime localDateTime, Region region) {
            return LocalDateTimeCalculator.createInstance(localDateTime, region);
        }
    }

    static class LocalDateCalculatorDelegate extends LocalDateCalculator {
        protected static LocalDateCalculator getInstance(ZonedDateTime zonedDateTime) {
            return LocalDateCalculator.createInstance(zonedDateTime);
        }
    }

    static class ChronoFormatTransformerSelectorDelegate extends ChronoFormatTransformerSelector {
        protected static ChronoFormatTransformerSelector getInstance() {
            return ChronoFormatTransformerSelector.createInstance();
        }
    }
}
