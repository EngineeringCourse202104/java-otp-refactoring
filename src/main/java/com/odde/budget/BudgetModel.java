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

        int dMonth = (int) (MONTHS.between(YearMonth.from(start), YearMonth.from(end)) + 1);

        if (dMonth <= 1) {
            return getBudgetByMonth(start, start.getDayOfMonth(), end.getDayOfMonth());
        }

        int sum = getBudgetByMonth(start, start.getDayOfMonth(), start.lengthOfMonth());

        for (int i = 0; i < dMonth - 2; i++) {
            LocalDate temp = start.plusMonths(i + 1);
            sum += getBudgetByMonth(temp, 1, temp.lengthOfMonth());
        }

        sum += getBudgetByMonth(end, 1, end.getDayOfMonth());

        return sum;
    }

    private int getBudgetByDay(Budget budget, int monthDays, int start, int end) {
        int avr = budget.amount / monthDays;
        return (end - start + 1) * avr;
    }

    private int getBudgetByMonth(LocalDate date, int start, int end) {
        for (int i = 0; i < getBudgets().size(); i++) {
            Budget budget = getBudgets().get(i);
            if (date.getYear() == budget.year && date.getMonthValue() == budget.month) {
                return getBudgetByDay(budget, date.lengthOfMonth(), start, end);
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }
}
