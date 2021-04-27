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

    private int getAmountOfPeriod(LocalDate startDate, LocalDate endDate, Budget budget) {
        return (endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1) * budget.getDailyAmount();
    }

    private int getBudgetByMonth(LocalDate startDate, LocalDate endDate) {
        for (Budget budget : getBudgets()) {
            if (budget.isSameMonth(startDate)) {
                return getAmountOfPeriod(startDate, endDate, budget);
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private int queryBudgetInPeriod(Period period) {
        int dMonth = (int) (MONTHS.between(yearMonth(period.getStart()), yearMonth(period.getEnd())) + 1);

        if (dMonth <= 1) {
            return getBudgetByMonth(period.getStart(), period.getEnd());
        }

        int sum = getBudgetByMonth(period.getStart(), yearMonth(period.getStart()).atEndOfMonth());

        for (int i = 0; i < dMonth - 2; i++) {
            LocalDate temp = period.getStart().plusMonths(i + 1);
            sum += getBudgetByMonth(yearMonth(temp).atDay(1), yearMonth(temp).atEndOfMonth());
        }

        sum += getBudgetByMonth(yearMonth(period.getEnd()).atDay(1), period.getEnd());

        return sum;
    }

    private YearMonth yearMonth(LocalDate start) {
        return YearMonth.from(start);
    }
}
