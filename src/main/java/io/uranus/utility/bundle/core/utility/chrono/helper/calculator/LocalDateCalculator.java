package io.uranus.utility.bundle.core.utility.chrono.helper.calculator;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class LocalDateCalculator {

    private ZonedDateTime zonedDateTime;

    protected LocalDateCalculator() {}

    private LocalDateCalculator(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    /**
     * @param zonedDateTime
     * 체이닝 연산을 위한 계산기 인스턴스를 생성합니다.
     */
    protected static LocalDateCalculator createInstance(ZonedDateTime zonedDateTime) {
        return new LocalDateCalculator(zonedDateTime);
    }

    /**
     * @param year
     * 현재 객체가 가진 [ZonedDateTime]의 년도를 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateCalculator fixYears(int year) {
        if (year == 0) {
            return this;
        }

        if (year < 0) {
            int abs = Math.abs(year);
            this.zonedDateTime = zonedDateTime.minusYears(abs);
        }
        if (year > 0) {
            this.zonedDateTime = zonedDateTime.plusYears(year);
        }

        return this;
    }

    /**
     * @param month
     * 현재 객체가 가진 [ZonedDateTime]의 월을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateCalculator fixMonths(int month) {
        if (month == 0) {
            return this;
        }

        if (month < 0) {
            int abs = Math.abs(month);
            this.zonedDateTime = zonedDateTime.minusMonths(abs);
        }
        if (month > 0) {
            this.zonedDateTime = zonedDateTime.plusMonths(month);
        }

        return this;
    }

    /**
     * @param day
     * 현재 객체가 가진 [ZonedDateTime]의 일자을 수정합니다.
     * 음수 연산을 지원합니다.
     */
    public LocalDateCalculator fixDays(int day) {
        if (day == 0) {
            return this;
        }

        if (day < 0) {
            int abs = Math.abs(day);
            this.zonedDateTime = zonedDateTime.minusDays(abs);
        }
        if (day > 0) {
            this.zonedDateTime = zonedDateTime.plusDays(day);
        }

        return this;
    }

    /**
     * 현재 객체가 가진 [ZonedDateTime] 기반의 [LocalDate]를 반환합니다.
     */
    public LocalDate compute() {
        return this.zonedDateTime.withHour(0).withMinute(0).withSecond(0).toLocalDate();
    }

    /**
     * 현재 객체가 가진 [ZonedDateTime] 기반의 [Epoch]를 반환합니다.
     */
    public Long computeToEpoch() {
        return this.zonedDateTime.withHour(0).withMinute(0).withSecond(0).toEpochSecond();
    }
}
