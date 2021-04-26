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

    private int queryBudgetInPeriod(Period period) {
        long dMonth = MONTHS.between(YearMonth.from(period.getStart()), YearMonth.from(period.getEnd()));

        if (dMonth <= 0) {
            return getBudgetByMonth(new Period(period.getStart(), period.getEnd()));
        }

        int sum = getBudgetByMonth(new Period(period.getStart(), YearMonth.from(period.getStart()).atEndOfMonth()));

        for (int i = 0; i < dMonth - 1; i++) {
            LocalDate temp = period.getStart().plusMonths(i + 1);
            sum += getBudgetByMonth(new Period(YearMonth.from(temp).atDay(1), YearMonth.from(temp).atEndOfMonth()));
        }

        sum += getBudgetByMonth(new Period(YearMonth.from(period.getEnd()).atDay(1), period.getEnd()));

        return sum;
    }
}
