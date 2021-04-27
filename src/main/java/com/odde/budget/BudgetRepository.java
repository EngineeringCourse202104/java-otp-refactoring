package com.odde.budget;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

class Budget {
    int year;
    int month;
    int amount;

    public int getOverlappingAmount(Period period) {
        return getDailyAmount() * period.getOverlappingDayCount(getPeriod());
    }

    private int getDailyAmount() {
        return amount / getYearMonth().lengthOfMonth();
    }

    private Period getPeriod() {
        return new Period(getYearMonth().atDay(1), getYearMonth().atEndOfMonth());
    }

    private YearMonth getYearMonth() {
        return YearMonth.of(year, month);
    }

}

public class BudgetRepository {
    public List<Budget> findAll() {
        return new ArrayList<>();
    }
}
