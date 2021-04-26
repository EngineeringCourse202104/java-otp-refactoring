package com.odde.budget;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static java.time.Period.between;
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

    private int getBudgetByMonth(LocalDate startDate, LocalDate endDate) {
        for (Budget budget : getBudgets()) {
            if (YearMonth.from(startDate).equals(budget.getYearMonth())) {
                return (between(startDate, endDate).getDays() + 1) * budget.getDailyAmount();
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
            return getBudgetByMonth(period.getStart(), period.getEnd());
        }

        int sum = getBudgetByMonth(period.getStart(), YearMonth.from(period.getStart()).atEndOfMonth());

        for (int i = 0; i < dMonth - 1; i++) {
            LocalDate temp = period.getStart().plusMonths(i + 1);
            sum += getBudgetByMonth(YearMonth.from(temp).atDay(1), YearMonth.from(temp).atEndOfMonth());
        }

        sum += getBudgetByMonth(YearMonth.from(period.getEnd()).atDay(1), period.getEnd());

        return sum;
    }
}
