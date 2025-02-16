package io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.ChronoFormat;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.base.ChronoTransformer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EpochToStringTransformer extends ChronoTransformer<String> {

    private Long target;
    private ChronoFormat format;

    private EpochToStringTransformer() {}

    public static EpochToStringTransformer createInstance() {
        return new EpochToStringTransformer();
    }

    public EpochToStringTransformer epoch(Long epoch) {
        this.target = epoch;
        return this;
    }

    public EpochToStringTransformer format(ChronoFormat format) {
        this.format = format;
        return this;
    }

    public EpochToStringTransformer region(Region region) {
        super.region = region;
        return this;
    }

    public String transform() {
        if (target == null || format == null) {
            throw new IllegalStateException("Epoch and format are required");
        }

        if (super.region == null) {
            super.region = Region.REPUBLIC_OF_KOREA;
        }

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.target), ZoneId.of(super.region.getTimeZone()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.format.getFormat());

        return formatter.format(localDateTime);
    }
}
