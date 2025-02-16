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

    /**
     * @return LocalDateTime of now with default region
     */
    public static LocalDateTime now() {
        return now(DEFAULT_REGION);
    }

    /**
     * @param region
     * @return LocalDateTime of now with region
     */
    public static LocalDateTime now(@Nullable Region region) {
        return LocalDateTime.now().atZone(ZoneId.of(withDefaultIfNecessary(region).getTimeZone())).toLocalDateTime();
    }

    /**
     * @return Epoch with default region
     */
    public static Long epoch() {
        return epoch(DEFAULT_REGION);
    }


    /**
     * @param region
     * @return Epoch with region
     */
    public static Long epoch(@Nullable Region region) {
        return ZonedDateTime.now(ZoneId.of(withDefaultIfNecessary(region).getTimeZone())).toEpochSecond();
    }

    /**
     * Provide LocalDateTime compute chain
     * @see LocalDateTimeCalculator
     * @return ChronoCalculator
     */
    public static LocalDateTimeCalculator dateTimeComputeChain() {
        return dateTimeComputeChain(now(DEFAULT_REGION), DEFAULT_REGION);
    }

    /**
     * Provide LocalDateTime compute chain
     * @see LocalDateTimeCalculator
     * @param region
     * @return LocalDateTimeCalculator
     */
    public static LocalDateTimeCalculator dateTimeComputeChain(@Nullable Region region) {
        return dateTimeComputeChain(now(withDefaultIfNecessary(region)), region);
    }

    /**
     * invoke constructor of LocalDateTimeCalculator
     * @see LocalDateTimeCalculator
     * @param localDateTime
     * @return LocalDateTimeCalculator
     */
    public static LocalDateTimeCalculator dateTimeComputeChain(@Nonnull LocalDateTime localDateTime, Region region) {
        return LocalDateTimeCalculator.createInstance(localDateTime, region);
    }

    /**
     * provide LocalDate compute chain
     * @see LocalDateCalculator
     * @return LocalDateCalculator
     */
    public static LocalDateCalculator dateComputeChain() {
        return dateComputeChain(ZonedDateTime.now(ZoneId.of(DEFAULT_REGION.getTimeZone())));
    }

    /**
     * provide LocalDate compute chain
     * @see LocalDateCalculator
     * @return LocalDateCalculator
     */
    public static LocalDateCalculator dateComputeChain(@Nullable Region region) {
        return dateComputeChain(ZonedDateTime.now(ZoneId.of(withDefaultIfNecessary(region).getTimeZone())));
    }

    /**
     * invoke constructor of LocalDateCalculator
     * @see LocalDateCalculator
     * @param zonedDateTime
     * @return LocalDateCalculator
     */
    public static LocalDateCalculator dateComputeChain(@Nonnull ZonedDateTime zonedDateTime) {
        return LocalDateCalculator.createInstance(zonedDateTime);
    }

    public static ChronoFormatTransformerSelector formatTransform() {
        return ChronoFormatTransformerSelector.createInstance();
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
}
