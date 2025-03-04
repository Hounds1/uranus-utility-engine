package io.uranus.utility.bundle.core.utility.chrono.helper.element;

import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public enum ChronoFormat {

    DEFAULT_FORMAT("yyyy-MM-dd HH:mm:ss"),
    SIMPLIFY_FORMAT("yyyyMMdd"),
    SIMPLIFY_WITH_DASH("yyyy-MM-dd"),
    EXCEL_FORMAT("yyyy-MM-dd (HH:mm:ss)"),
    TIME_ONLY_FORMAT("%02d:%02d:%02d"),
    HOUR_ONLY_FORMAT("HH"),
    JSON_EXPORT_FORMAT_WINDOWS("yyyy\\MM\\dd"),
    JSON_EXPORT_FORMAT_LINUX("yyyy/MM/dd"),
    ;

    private final String format;

    ChronoFormat(String format) {
        this.format = format;
    }

    public static List<DateTimeFormatter> loadAllFormatters() {
        List<DateTimeFormatter> formats = new ArrayList<>();

        for (ChronoFormat value : values()) {
            formats.add(DateTimeFormatter.ofPattern(value.format));
        }

        return formats;
    }
}
