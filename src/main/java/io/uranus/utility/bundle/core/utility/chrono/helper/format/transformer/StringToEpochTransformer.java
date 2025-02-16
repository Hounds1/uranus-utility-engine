package io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.ChronoFormat;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.base.ChronoTransformer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StringToEpochTransformer extends ChronoTransformer<Long> {

    private String target;

    protected StringToEpochTransformer() {}

    protected static StringToEpochTransformer createInstance() {
        return new StringToEpochTransformer();
    }

    public StringToEpochTransformer target(String target) {
        this.target = target;
        return this;
    }

    public StringToEpochTransformer region(Region region) {
        super.region = region;
        return this;
    }

    public Long transform() {
        if (this.target == null) {
            throw new IllegalStateException("String datetime(target) is required.");
        }

        if (super.region == null) {
            super.region = Region.REPUBLIC_OF_KOREA;
        }

        List<DateTimeFormatter> formatters = ChronoFormat.loadAllFormatters();

        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(this.target, formatter);

                return localDateTime.atZone(ZoneId.of(super.region.getTimeZone())).toEpochSecond();
            } catch (Exception suppressed) {
            }
        }

        throw new RuntimeException("Could not transform string to epoch");
    }
}
