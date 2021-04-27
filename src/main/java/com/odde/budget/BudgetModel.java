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

    private int allMonthCount(Period period) {
        return (int) (MONTHS.between(yearMonth(period.getStart()), yearMonth(period.getEnd())) + 1);
    }

    private int getAmountInPeriod(Period period, Budget budget) {
        return period.getDayCount() * budget.getDailyAmount();
    }

    private int getBudgetByMonth(Period period) {
        for (Budget budget : getBudgets()) {
            if (budget.isSameMonth(period.getStart())) {
                return getAmountInPeriod(period, budget);
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private Period getOverlappingPeriod(Period period, YearMonth month) {
        LocalDate overlappingStart = period.getStart().isAfter(month.atDay(1)) ? period.getStart() : month.atDay(1);
        LocalDate overlappingEnd = period.getEnd().isBefore(month.atEndOfMonth()) ? period.getEnd() : month.atEndOfMonth();
        return new Period(overlappingStart, overlappingEnd);
    }

    private int queryBudgetInPeriod(Period period) {
        int sum = 0;

        for (int i = 0; i < allMonthCount(period); i++) {
            LocalDate temp = period.getStart().plusMonths(i);
            sum += getBudgetByMonth(getOverlappingPeriod(period, yearMonth(temp)));
        }

        return sum;
    }

    private YearMonth yearMonth(LocalDate start) {
        return YearMonth.from(start);
    }
}
