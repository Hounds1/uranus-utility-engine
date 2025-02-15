package io.uranus.utility.bundle.core.utility.chrono.helper.calculator;

import java.time.LocalDateTime;

public class LocalDateTimeCalculator {

    private LocalDateTime localDateTime;

    private LocalDateTimeCalculator() {}

    private LocalDateTimeCalculator(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public static LocalDateTimeCalculator createInstance(LocalDateTime initialized) {
        return new LocalDateTimeCalculator(initialized);
    }

    public LocalDateTimeCalculator fixYears(int year) {
        if (year == 0) {
            return this;
        }

        if (year < 0) {
            this.localDateTime = this.localDateTime.minusYears(year);
        }
        if (year > 0) {
            this.localDateTime = this.localDateTime.plusYears(year);
        }

        return this;
    }

    public LocalDateTimeCalculator fixMonths(int month) {
        if (month == 0) {
            return this;
        }

        if (month < 0) {
            this.localDateTime = this.localDateTime.minusMonths(month);
        }
        if (month > 0) {
            this.localDateTime = this.localDateTime.plusMonths(month);
        }

        return this;
    }

    public LocalDateTimeCalculator fixDays(int day) {
        if (day == 0) {
            return this;
        }

        if (day < 0) {
            this.localDateTime = this.localDateTime.minusDays(day);
        }
        if (day > 0) {
            this.localDateTime = this.localDateTime.plusDays(day);
        }

        return this;
    }

    public LocalDateTimeCalculator fixHours(int hour) {
        if (hour == 0) {
            return this;
        }

        if (hour < 0) {
            this.localDateTime = this.localDateTime.minusHours(hour);
        }
        if (hour > 0) {
            this.localDateTime = this.localDateTime.plusHours(hour);
        }

        return this;
    }

    public LocalDateTimeCalculator fixMinutes(int minute) {
        if (minute == 0) {
            return this;
        }

        if (minute < 0) {
            this.localDateTime = this.localDateTime.minusMinutes(minute);
        }
        if (minute > 0) {
            this.localDateTime = this.localDateTime.plusMinutes(minute);
        }

        return this;
    }

    public LocalDateTimeCalculator fixSeconds(int second) {
        if (second == 0) {
            return this;
        }

        if (second < 0) {
            this.localDateTime = this.localDateTime.minusSeconds(second);
        }
        if (second > 0) {
            this.localDateTime = this.localDateTime.plusSeconds(second);
        }

        return this;
    }

    public LocalDateTime compute() {
        return this.localDateTime;
    }
}
