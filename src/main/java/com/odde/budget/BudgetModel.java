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

        long dMonth = MONTHS.between(YearMonth.from(start), YearMonth.from(end));

        if (dMonth <= 0) {
            return getBudgetByMonth(start, start.getDayOfMonth(), end.getDayOfMonth());
        }

        int sum = getBudgetByMonth(start, start.getDayOfMonth(), start.lengthOfMonth());

        for (int i = 0; i < dMonth - 1; i++) {
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
            if (date.getYear() == getBudgets().get(i).year && date.getMonthValue() == getBudgets().get(i).month) {
                Budget budget = getBudgets().get(i);

                if (end < start || start < 1 || end > date.lengthOfMonth()) {
                    return 0;
                }

                return getBudgetByDay(budget, date.lengthOfMonth(), start, end);
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }
}
