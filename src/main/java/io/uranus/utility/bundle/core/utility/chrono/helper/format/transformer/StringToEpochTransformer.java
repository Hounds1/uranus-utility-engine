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

    /**
     * String => Epoch 포맷 변환 체인 인스턴스을 반환합니다.
     */
    protected static StringToEpochTransformer createInstance() {
        return new StringToEpochTransformer();
    }

    /**
     * @param target
     * 수정 대상이 되는 문자열 시간 [target]을 설정합니다.
     */
    public StringToEpochTransformer withTarget(String target) {
        this.target = target;
        return this;
    }

    /**
     * @param region
     * 수정 시간 보정이 적용 될 [Region]을 설정합니다.
     */
    public StringToEpochTransformer withRegion(Region region) {
        super.region = region;
        return this;
    }

    public StringToEpochTransformer withDefaultRegion(Region region) {
        super.withDefaultRegion();
        return this;
    }

    /**
     * @return Epoch
     * @throws IllegalArgumentException
     * 형 변환을 시도하고 반환합니다.
     * 필수 원소가 존재하지 않을 시 예외를 발생시킵니다.
     */
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
