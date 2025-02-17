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

    /**
     * String => String 포맷 변환 체인 인스턴스을 반환합니다.
     */
    protected static StringToStringTransformer createInstance() {
        return new StringToStringTransformer();
    }

    /**
     * @param target
     * 수정 대상이 되는 문자열 시간 [target]을 설정합니다.
     */
    public StringToStringTransformer target(String target) {
        this.target = target;
        return this;
    }

    /**
     * @param region
     * 수정 시간 보정이 적용 될 [Region]을 설정합니다.
     */
    public StringToStringTransformer region(Region region) {
        super.region = region;
        return this;
    }

    /**
     * @param format(ChronoFormat)
     * 변환 대상 포맷을 설정합니다.
     */
    public StringToStringTransformer format(ChronoFormat format) {
        this.format = format;
        return this;
    }

    /**
     * @return String Formatted Datetime
     * @throws IllegalArgumentException
     * 형 변환을 시도하고 반환합니다.
     * 필수적인 원소가 존재하지 않을 경우 예외를 발생시킵니다.
     */
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
