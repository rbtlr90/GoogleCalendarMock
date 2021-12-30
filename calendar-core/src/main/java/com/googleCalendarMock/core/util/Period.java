package com.googleCalendarMock.core.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Period {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    private Period(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static Period of(LocalDateTime startAt, LocalDateTime endAt) {
        return new Period(startAt, endAt == null ? startAt : endAt);
    }

    public static Period of(LocalDate startDate, LocalDate endDate) {
        return new Period(startDate.atStartOfDay(),
                LocalDateTime.of(endDate, LocalTime.of(23, 59, 59, 999999999)));
    }

    public boolean isOverlapped(Period period) {
        return isOverlapped(period.startAt, period.endAt);
    }
    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.startAt.isBefore(endAt) && startAt.isBefore(this.endAt);
    }

    public boolean isOverlapped(LocalDate date) {
        return isOverlapped(date.atStartOfDay(),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59, 999999999)));
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }
}
