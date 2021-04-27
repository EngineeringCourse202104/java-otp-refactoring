package com.odde.budget;

import java.time.LocalDate;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getDayCount() {
        return java.time.Period.between(start, end).getDays() + 1;
    }

    public Period getOverlappingPeriod(Period anotherPeriod) {
        LocalDate overlappingStart = getStart().isAfter(anotherPeriod.getStart()) ? getStart() : anotherPeriod.getStart();
        LocalDate overlappingEnd = getEnd().isBefore(anotherPeriod.getEnd()) ? getEnd() : anotherPeriod.getEnd();
        if (overlappingStart.isAfter(overlappingEnd)) {
            return new Period(overlappingStart, overlappingEnd) {
                @Override
                public int getDayCount() {
                    return 0;
                }
            };
        }
        return new Period(overlappingStart, overlappingEnd);
    }
}
