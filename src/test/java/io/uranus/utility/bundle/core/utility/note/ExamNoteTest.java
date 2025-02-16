package io.uranus.utility.bundle.core.utility.note;

import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExamNoteTest {

    @Test
    void invokeDateTimeComputeChain() {
        LocalDateTime localDateTime = ChronoHelper.dateTimeComputeChain()
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .fixHours(1)
                .fixMinutes(10)
                .fixSeconds(1)
                .compute();

        System.out.println("invokeDateTimeComputeChain " + localDateTime);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator
     *
     * @implNote Invoke computation chain for create LocalDate
     */
    @Test
    void invokeDateComputeChain() {
        LocalDate date = ChronoHelper.dateComputeChain()
                .fixYears(1)
                .fixMonths(2)
                .fixDays(10)
                .compute();

        System.out.println("invokeDateComputeChain " + date);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateTimeCalculator
     *
     * @implNote Invoke computation chain with region for create LocalDateTime
     */
    @Test
    void invokeDateTImeComputeChainWithRegion() {
        LocalDateTime localDateTime = ChronoHelper.dateTimeComputeChain(Region.REPUBLIC_OF_KOREA)
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .fixHours(1)
                .fixMinutes(10)
                .fixSeconds(1)
                .compute();

        System.out.println("invokeDateTImeComputeChainWithRegion " + localDateTime);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator
     *
     * @implNote Invoke computation chain with region for create LocalDate
     */
    @Test
    void invokeDateComputeChainWithRegion() {
        LocalDate localDate = ChronoHelper.dateComputeChain(Region.REPUBLIC_OF_KOREA)
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .compute();

        System.out.println("invokeDateComputeChainWithRegion " + localDate);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create now with default region(timezone)
     */
    @Test
    void createNowWithDefaultRegion() {
        LocalDateTime nowWithNoneParam = ChronoHelper.now();

        LocalDateTime nowWithNullParam = ChronoHelper.now(null);

        System.out.println("createNowWithDefaultRegion " + nowWithNoneParam);
        System.out.println("createNowWithDefaultRegion " + nowWithNullParam);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create now with region(timezone)
     */
    @Test
    void createNowWithRegion() {
        LocalDateTime now = ChronoHelper.now(Region.REPUBLIC_OF_KOREA);

        System.out.println("createNowWithRegion " + now);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create epoch of now with default region
     */
    @Test
    void createEpochWithDefaultRegion() {
        Long epochWithNoneParam = ChronoHelper.epoch();
        LocalDateTime epochWithNullParam = ChronoHelper.now(null);

        System.out.println("createEpochWithDefaultRegion " + epochWithNoneParam);
        System.out.println("createEpochWithDefaultRegion " + epochWithNullParam);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create epoch of now with region(timezone)
     */
    @Test
    void createEpochWithRegion() {
        Long epochWithRegion = ChronoHelper.epoch(Region.REPUBLIC_OF_KOREA);

        System.out.println("createEpochWithDefaultRegion " + epochWithRegion);
    }

}