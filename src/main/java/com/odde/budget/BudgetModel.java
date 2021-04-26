package com.odde.budget;


import java.time.LocalDate;
import java.time.Period;
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

        long dMonth = MONTHS.between(YearMonth.from(start), YearMonth.from(end));

        if (dMonth <= 0) {
            return getBudgetByMonth(start, end);
        }

        int sum = getBudgetByMonth(start, YearMonth.from(start).atEndOfMonth());

        for (int i = 0; i < dMonth - 1; i++) {
            LocalDate temp = start.plusMonths(i + 1);
            sum += getBudgetByMonth(YearMonth.from(temp).atDay(1), YearMonth.from(temp).atEndOfMonth());
        }

        sum += getBudgetByMonth(YearMonth.from(end).atDay(1), end);

        return sum;
    }

    private int getBudgetByMonth(LocalDate startDate, LocalDate endDate) {
        for (Budget budget : getBudgets()) {
            if (YearMonth.from(startDate).equals(budget.getYearMonth())) {
                return (Period.between(startDate, endDate).getDays() + 1) * (budget.amount / budget.getYearMonth().lengthOfMonth());
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }
}
