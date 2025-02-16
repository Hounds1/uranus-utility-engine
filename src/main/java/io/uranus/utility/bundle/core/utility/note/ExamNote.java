package io.uranus.utility.bundle.core.utility.note;

import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExamNote {

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateTimeCalculator
     *
     * @implNote Invoke computation chain for create LocalDateTime
     */
    private void invokeDateTimeComputeChain() {
        LocalDateTime localDateTime = ChronoHelper.dateTimeComputeChain()
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .fixHours(1)
                .fixMinutes(10)
                .fixSeconds(1)
                .compute();
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator
     *
     * @implNote Invoke computation chain for create LocalDate
     */
    private void invokeDateComputeChain() {
        LocalDate date = ChronoHelper.dateComputeChain()
                .fixYears(1)
                .fixMonths(2)
                .fixDays(10)
                .compute();
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateTimeCalculator
     *
     * @implNote Invoke computation chain with region for create LocalDateTime
     */
    private void invokeDateTImeComputeChainWithRegion() {
        LocalDateTime localDateTime = ChronoHelper.dateTimeComputeChain(Region.REPUBLIC_OF_KOREA)
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .fixHours(1)
                .fixMinutes(10)
                .fixSeconds(1)
                .compute();
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator
     *
     * @implNote Invoke computation chain with region for create LocalDate
     */
    private void invokeDateComputeChainWithRegion() {
        LocalDate localDate = ChronoHelper.dateComputeChain(Region.REPUBLIC_OF_KOREA)
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .compute();
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create now with default region(timezone)
     */
    private void createNowWithDefaultRegion() {
        LocalDateTime nowWithNoneParam = ChronoHelper.now();

        LocalDateTime nowWithNullParam = ChronoHelper.now(null);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create now with region(timezone)
     */
    private void createNowWithRegion() {
        LocalDateTime now = ChronoHelper.now(Region.REPUBLIC_OF_KOREA);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create epoch of now with default region
     */
    private void createEpochWithDefaultRegion() {
        Long epochWithNoneParam = ChronoHelper.epoch();
        LocalDateTime epochWithNullParam = ChronoHelper.now(null);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create epoch of now with region(timezone)
     */
    private void createEpochWithRegion() {
        Long epochWithRegion = ChronoHelper.epoch(Region.REPUBLIC_OF_KOREA);
    }
}
