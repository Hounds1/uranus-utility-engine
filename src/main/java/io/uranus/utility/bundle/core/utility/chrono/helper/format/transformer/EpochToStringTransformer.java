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

    protected EpochToStringTransformer() {}

    /**
     * Epoch => String 변환 체인 인스턴스를 생성하고 반환합니다.
     */
    protected static EpochToStringTransformer createInstance() {
        return new EpochToStringTransformer();
    }

    /**
     * @param epoch
     * 변환 대상이 되는 [Epoch]를 설정합니다.
     */
    public EpochToStringTransformer withEpoch(Long epoch) {
        this.target = epoch;
        return this;
    }

    /**
     * @param format
     * [Epoch]를 변환 할 [Format]을 설정합니다.
     */
    public EpochToStringTransformer withFormat(ChronoFormat format) {
        this.format = format;
        return this;
    }

    /**
     * @param region
     * 변환 시간대 보정을 위한 [Region]을 설정합니다.
     */
    public EpochToStringTransformer withRegion(Region region) {
        super.region = region;
        return this;
    }

    public EpochToStringTransformer withDefaultRegion() {
        super.withDefaultRegion();
        return this;
    }

    /**
     * @return String Formatted Datetime
     * @throws IllegalArgumentException
     * 형 변환을 시도하고 반환합니다.
     * 필수적인 원소가 존재하지 않을 경우 예외를 발생시킵니다.
     */
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
