package io.uranus.utility.bundle.core.utility.chrono.helper.element;

import lombok.Getter;

@Getter
public enum ChronoFormat {

    DEFAULT_TIME_ZONE("Asia/Seoul"),
    DEFAULT_FORMAT("yyyy-MM-dd HH:mm:ss"),
    SIMPLIFY_FORMAT("yyyyMMdd"),
    SIMPLIFY_WITH_DASH("yyyy-MM-dd"),
    EXCEL_FORMAT("yyyy-MM-dd (HH:mm:ss)"),
    TIME_ONLY_FORMAT("%02d:%02d:%02d"),
    HOUR_ONLY_FORMAT("HH");

    private final String format;

    ChronoFormat(String format) {
        this.format = format;
    }
}
