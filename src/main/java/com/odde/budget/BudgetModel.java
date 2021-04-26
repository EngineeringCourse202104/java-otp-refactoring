package com.odde.budget;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static java.time.temporal.ChronoUnit.MONTHS;

public class BudgetModel {

    BudgetRepository repository;

    public BudgetModel(BudgetRepository repository) {
        this.repository = repository;
    }

    public int queryBudget(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0;
        }

        return queryBudgetInPeriod(new Period(start, end));
    }

    private int getBudgetByMonth(Period period) {
        for (Budget budget : getBudgets()) {
            if (YearMonth.from(period.getStart()).equals(budget.getYearMonth())) {
                return period.getDayCount() * budget.getDailyAmount();
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private Period getOverlappingPeriod(Period period, YearMonth yearMonth) {
        LocalDate overlappingStart = period.getStart().isAfter(yearMonth.atDay(1)) ? period.getStart() : yearMonth.atDay(1);
        LocalDate overlappingEnd = period.getEnd().isBefore(yearMonth.atEndOfMonth()) ? period.getEnd() : yearMonth.atEndOfMonth();
        return new Period(overlappingStart, overlappingEnd);
    }

    private int queryBudgetInPeriod(Period period) {
        long dMonth = MONTHS.between(YearMonth.from(period.getStart()), YearMonth.from(period.getEnd()));

        int sum = 0;

        for (int i = 0; i < dMonth + 1; i++) {
            YearMonth yearMonth = YearMonth.from(period.getStart().plusMonths(i));
            sum += getBudgetByMonth(getOverlappingPeriod(period, yearMonth));
        }

        return sum;
    }
}
