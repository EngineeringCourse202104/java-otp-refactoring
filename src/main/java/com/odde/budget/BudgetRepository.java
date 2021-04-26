package com.odde.budget;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

class Budget {
    int year;
    int month;
    int amount;

    public YearMonth getYearMonth() {
        return YearMonth.of(year, month);
    }

    public int getDailyAmount() {
        return amount / getYearMonth().lengthOfMonth();
    }
}

public class BudgetRepository {
    public List<Budget> findAll() {
        return new ArrayList<>();
    }
}
