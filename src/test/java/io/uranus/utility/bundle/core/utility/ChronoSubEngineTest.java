package io.uranus.utility.bundle.core.utility;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.ChronoFormat;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class ChronoSubEngineTest {

    @Test
    void createNow() {
        LocalDateTime now = UranusUtilityEngine.chrono().now();

        System.out.println(now);
    }

    @Test
    void createNowWithRegion() {
        LocalDateTime now = UranusUtilityEngine.chrono().now(Region.UNITED_STATES_CENTRAL);

        System.out.println(now);
    }

    @Test
    void createEpoch() {
        Long epoch = UranusUtilityEngine.chrono().epoch();

        System.out.println(epoch);
    }

    @Test
    void createEpochWithRegion() {
        Long epoch = UranusUtilityEngine.chrono().epoch(Region.UNITED_STATES_CENTRAL);

        System.out.println(epoch);
    }

    @Test
    void dateTimeComputeChain() {
        LocalDateTime compute = UranusUtilityEngine.chrono().dateTimeComputeChain(Region.UNITED_STATES_CENTRAL)
                .fixYears(1)
                .fixMonths(-1)
                .compute();

        System.out.println(compute);

        Long toEpoch = UranusUtilityEngine.chrono().dateTimeComputeChain(Region.UNITED_STATES_CENTRAL)
                .fixYears(1)
                .fixMonths(-1)
                .computeToEpoch();

        System.out.println(toEpoch);
    }

    @Test
    void dateComputeChain() {
        LocalDate compute = UranusUtilityEngine.chrono().dateComputeChain()
                .fixYears(1)
                .fixMonths(-1)
                .fixDays(-1)
                .compute();

        System.out.println(compute);

        Long toEpoch = UranusUtilityEngine.chrono().dateComputeChain()
                .fixYears(1)
                .fixMonths(-1)
                .fixDays(-1)
                .computeToEpoch();

        System.out.println(toEpoch);
    }

    @Test
    void formatTransform() {
        final String base = "2025-02-19 23:09:00";
        final Long epoch = UranusUtilityEngine.chrono().epoch();

        String transformedEpochToString = UranusUtilityEngine.chrono().formatTransform()
                .epochToString()
                .withEpoch(epoch)
                .withFormat(ChronoFormat.DEFAULT_FORMAT)
                .withRegion(Region.UNITED_STATES_CENTRAL)
                .transform();

        System.out.println(transformedEpochToString);

        Long transformedStringToEpoch = UranusUtilityEngine.chrono().formatTransform()
                .stringToEpoch()
                .withTarget(base)
                .withDefaultRegion()
                .transform();

        System.out.println(transformedStringToEpoch);

        String transformedStringToString = UranusUtilityEngine.chrono().formatTransform()
                .stringToString()
                .withTarget(base)
                .withRegion(Region.UNITED_STATES_CENTRAL)
                .withFormat(ChronoFormat.EXCEL_FORMAT)
                .transform();

        System.out.println(transformedStringToString);
    }
}
