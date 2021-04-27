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

        return queryBudgetInPeriod(new Period(start, end));
    }

    private List<Budget> getBudgets() {
        return repository.findAll();
    }

    private int queryBudgetInPeriod(Period period) {
        return getBudgets().stream()
                .mapToInt(budget -> budget.getOverlappingAmount(period))
                .sum();
    }

}
