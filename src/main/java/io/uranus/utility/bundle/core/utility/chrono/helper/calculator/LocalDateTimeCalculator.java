package io.uranus.utility.bundle.core.utility.chrono.helper.calculator;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeCalculator {

    private LocalDateTime localDateTime;
    private Region region;

    protected LocalDateTimeCalculator() {}

    private LocalDateTimeCalculator(LocalDateTime localDateTime, Region region) {
        this.localDateTime = localDateTime;
    }

    /**
     * @param initialized
     * @param region
     * 체이닝 연산을 지원하는 계산기 인스턴스를 생성하고 반환합니다.
     */
    protected static LocalDateTimeCalculator createInstance(LocalDateTime initialized, Region region) {
        return new LocalDateTimeCalculator(initialized, region);
    }

    /**
     * @param year
     * 현재 객체가 가진 [LocalDateTime]의 년도를 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateTimeCalculator fixYears(int year) {
        if (year == 0) {
            return this;
        }

        if (year < 0) {
            int abs = Math.abs(year);
            this.localDateTime = this.localDateTime.minusYears(abs);
        }
        if (year > 0) {
            this.localDateTime = this.localDateTime.plusYears(year);
        }

        return this;
    }

    /**
     * @param month
     * 현재 객체가 가진 [LocalDateTime]의 월을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateTimeCalculator fixMonths(int month) {
        if (month == 0) {
            return this;
        }

        if (month < 0) {
            int abs = Math.abs(month);
            this.localDateTime = this.localDateTime.minusMonths(abs);
        }
        if (month > 0) {
            this.localDateTime = this.localDateTime.plusMonths(month);
        }

        return this;
    }

    /**
     * @param day
     * 현재 객체가 가진 [LocalDateTime]의 일자을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateTimeCalculator fixDays(int day) {
        if (day == 0) {
            return this;
        }

        if (day < 0) {
            int abs = Math.abs(day);
            this.localDateTime = this.localDateTime.minusDays(abs);
        }
        if (day > 0) {
            this.localDateTime = this.localDateTime.plusDays(day);
        }

        return this;
    }

    /**
     * @param hour
     * 현재 객체가 가진 [LocalDateTime]의 일자을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateTimeCalculator fixHours(int hour) {
        if (hour == 0) {
            return this;
        }

        if (hour < 0) {
            int abs = Math.abs(hour);
            this.localDateTime = this.localDateTime.minusHours(abs);
        }
        if (hour > 0) {
            this.localDateTime = this.localDateTime.plusHours(hour);
        }

        return this;
    }

    /**
     * @param minute
     * 현재 객체가 가진 [LocalDateTime]의 일자을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateTimeCalculator fixMinutes(int minute) {
        if (minute == 0) {
            return this;
        }

        if (minute < 0) {
            int abs = Math.abs(minute);
            this.localDateTime = this.localDateTime.minusMinutes(abs);
        }
        if (minute > 0) {
            this.localDateTime = this.localDateTime.plusMinutes(minute);
        }

        return this;
    }

    /**
     * @param second
     * 현재 객체가 가진 [LocalDateTime]의 일자을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateTimeCalculator fixSeconds(int second) {
        if (second == 0) {
            return this;
        }

        if (second < 0) {
            int abs = Math.abs(second);
            this.localDateTime = this.localDateTime.minusSeconds(abs);
        }
        if (second > 0) {
            this.localDateTime = this.localDateTime.plusSeconds(second);
        }

        return this;
    }

    /**
     * 계산된 [LocalDateTime]을 반환합니다.
     */
    public LocalDateTime compute() {
        if (this.region == null) {
            this.region = Region.REPUBLIC_OF_KOREA;
        }

        return this.localDateTime;
    }

    /**
     * 지역 시간대를 적용한 [Epoch]를 반환합니다.
     */
    public Long computeToEpoch() {
        if (this.region == null) {
            this.region = Region.REPUBLIC_OF_KOREA;
        }

        return this.localDateTime.toEpochSecond(ZoneOffset.of(region.zoneOffset()));
    }
}
