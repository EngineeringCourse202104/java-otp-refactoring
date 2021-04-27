package com.odde.budget;

import java.time.LocalDate;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public int getOverlappingDayCount(Period anotherPeriod) {
        LocalDate overlappingStart = start.isAfter(anotherPeriod.start) ? start : anotherPeriod.start;
        LocalDate overlappingEnd = end.isBefore(anotherPeriod.end) ? end : anotherPeriod.end;
        if (overlappingStart.isAfter(overlappingEnd)) {
            return 0;
        }
        return new Period(overlappingStart, overlappingEnd).getDayCount();
    }

    private int getDayCount() {
        return java.time.Period.between(start, end).getDays() + 1;
    }

}
