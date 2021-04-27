package com.odde.budget;


import java.time.LocalDate;

public class BudgetModel {

    BudgetRepository repository;

    public BudgetModel(BudgetRepository repository) {
        this.repository = repository;
    }

    public int queryBudget(LocalDate start, LocalDate end) {
        return repository.findAll().stream()
                .mapToInt(budget -> budget.getOverlappingAmount(new Period(start, end)))
                .sum();
    }

}
