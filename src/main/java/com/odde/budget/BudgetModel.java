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

        int dMonth = (int) (MONTHS.between(yearMonth(start), yearMonth(end)) + 1);

        if (dMonth <= 1) {
            return getBudgetByMonth(start, end);
        }

        int sum = getBudgetByMonth(start, yearMonth(start).atEndOfMonth());

        for (int i = 0; i < dMonth - 2; i++) {
            LocalDate temp = start.plusMonths(i + 1);
            sum += getBudgetByMonth(yearMonth(temp).atDay(1), yearMonth(temp).atEndOfMonth());
        }

        sum += getBudgetByMonth(yearMonth(end).atDay(1), end);

        return sum;
    }

    private int getAmountOfPeriod(LocalDate startDate, LocalDate endDate, Budget budget) {
        int avr = budget.amount / startDate.lengthOfMonth();
        return (endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1) * avr;
    }

    private int getBudgetByMonth(LocalDate startDate, LocalDate endDate) {
        for (Budget budget : getBudgets()) {
            if (yearMonth(startDate).equals(budget.getYearMonth())) {
                return getAmountOfPeriod(startDate, endDate, budget);
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private YearMonth yearMonth(LocalDate start) {
        return YearMonth.from(start);
    }
}
