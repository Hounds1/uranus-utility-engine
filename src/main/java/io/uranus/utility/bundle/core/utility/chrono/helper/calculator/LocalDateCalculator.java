package io.uranus.utility.bundle.core.utility.chrono.helper.calculator;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class LocalDateCalculator {

    private ZonedDateTime zonedDateTime;

    private LocalDateCalculator() {}

    private LocalDateCalculator(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public static LocalDateCalculator createInstance(ZonedDateTime zonedDateTime) {
        return new LocalDateCalculator(zonedDateTime);
    }

    public LocalDateCalculator fixYears(int year) {
        if (year == 0) {
            return this;
        }

        if (year < 0) {
            this.zonedDateTime = zonedDateTime.minusYears(year);
        }
        if (year > 0) {
            this.zonedDateTime = zonedDateTime.plusYears(year);
        }

        return this;
    }

    public LocalDateCalculator fixMonths(int month) {
        if (month == 0) {
            return this;
        }

        if (month < 0) {
            this.zonedDateTime = zonedDateTime.minusMonths(month);
        }
        if (month > 0) {
            this.zonedDateTime = zonedDateTime.plusMonths(month);
        }

        return this;
    }

    public LocalDateCalculator fixDays(int day) {
        if (day == 0) {
            return this;
        }

        if (day < 0) {
            this.zonedDateTime = zonedDateTime.minusDays(day);
        }
        if (day > 0) {
            this.zonedDateTime = zonedDateTime.plusDays(day);
        }

        return this;
    }

    public LocalDate compute() {
        return this.zonedDateTime.toLocalDate();
    }
}
