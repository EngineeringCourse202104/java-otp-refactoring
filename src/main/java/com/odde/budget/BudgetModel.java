package com.odde.budget;


import java.time.LocalDate;
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

        int startYear = start.getYear();

        int startMonth = start.getMonthValue();

        int endYear = end.getYear();
        int endMonth = end.getMonthValue();

        int dMonth = (endYear * 12 + endMonth) - (startYear * 12 + startMonth) + 1;

        if (dMonth > 1) {

            int sum1 = getBudgetByMonth(start, start.getDayOfMonth(), start.lengthOfMonth());

            int sum2 = 0;

            for (int i = 0; i < dMonth - 2; i++) {

                LocalDate temp = start.plusMonths(i + 1);
                sum2 += getBudgetByMonth(temp, 0, 0);
            }

            int sum3 = getBudgetByMonth(end, 1, end.getDayOfMonth());

            return sum1 + sum2 + sum3;

        } else {
            return getBudgetByMonth(start, start.getDayOfMonth(), end.getDayOfMonth());
        }
    }

    private int getBudgetByMonth(LocalDate date, int start, int end) {


        Budget budget;


        for (int i = 0; i < getBudgets().size(); i++) {

            if (date.getYear() == getBudgets().get(i).year && date.getMonthValue() == getBudgets().get(i).month) {
                budget = getBudgets().get(i);

                if (start == 0 || end == 0) {
                    return budget.amount;
                } else {
                    if (end < start || start < 1 || end > date.lengthOfMonth()) {
                        return 0;
                    }

                    return getBudgetByDay(budget, date.lengthOfMonth(), start, end);
                }
            }
        }

        return 0;
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private int getBudgetByDay(Budget budget, int monthDays, int start, int end) {
        int avr = budget.amount / monthDays;
        return (end - start + 1) * avr;
    }
}
