package com.odde.budget;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private int getOverlappingDayCount(Period period, Budget budget) {
        YearMonth yearMonth = budget.getYearMonth();
        LocalDate overlappingStart = period.getStart().isAfter(yearMonth.atDay(1)) ? period.getStart() : yearMonth.atDay(1);
        LocalDate overlappingEnd = period.getEnd().isBefore(yearMonth.atEndOfMonth()) ? period.getEnd() : yearMonth.atEndOfMonth();
        if (overlappingStart.isAfter(overlappingEnd)) {
            return 0;
        }
        return new Period(overlappingStart, overlappingEnd).getDayCount();
    }

    private int queryBudgetInPeriod(Period period) {
        int sum = 0;

        for (Budget budget : getBudgets()) {
            sum += getOverlappingDayCount(period, budget) * budget.getDailyAmount();
        }

        return sum;
    }
}
