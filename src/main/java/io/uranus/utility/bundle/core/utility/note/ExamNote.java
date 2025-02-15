package io.uranus.utility.bundle.core.utility.note;

import io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExamNote {

    private void note() {
        LocalDateTime dateTime = ChronoHelper.dateTimeComputeChain().fixYears(-1).compute();

        LocalDate date = ChronoHelper.dateComputeChain().fixYears(1).compute();

        LocalDateTime now = ChronoHelper.now();
        LocalDateTime now1 = ChronoHelper.now(null);
        LocalDateTime now2 = ChronoHelper.now(Region.REPUBLIC_OF_KOREA);
    }
}
