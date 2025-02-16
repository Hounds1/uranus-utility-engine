package io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.ChronoFormat;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.base.ChronoTransformer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StringToStringTransformer extends ChronoTransformer<String> {

    private String target;
    private ChronoFormat format;

    protected StringToStringTransformer() {}

    protected static StringToStringTransformer createInstance() {
        return new StringToStringTransformer();
    }

    public StringToStringTransformer target(String target) {
        this.target = target;
        return this;
    }

    public StringToStringTransformer region(Region region) {
        super.region = region;
        return this;
    }

    public StringToStringTransformer format(ChronoFormat format) {
        this.format = format;
        return this;
    }

    public String transform() {
        if (this.target == null || this.format == null) {
            throw new IllegalArgumentException("String chrono(target) and format are required");
        }

        if (super.region == null) {
            super.region = Region.REPUBLIC_OF_KOREA;
        }

        List<DateTimeFormatter> formatters = ChronoFormat.loadAllFormatters();

        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(this.target, formatter);

                return DateTimeFormatter.ofPattern(this.format.getFormat()).format(localDateTime);
            } catch (Exception suppressed) {
            }
        }

        throw new IllegalArgumentException("Could not transform string to epoch. String chrono(target) may not be correct.");
    }
}
