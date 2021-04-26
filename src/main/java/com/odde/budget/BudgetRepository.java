package com.odde.budget;

import com.odde.securetoken.ProfileDao;
import com.odde.securetoken.RsaTokenDao;

import java.util.ArrayList;
import java.util.List;

class Budget {
    int year;
    int month;
    int amount;
}

public class BudgetRepository {
    public List<Budget> findAll() {
        return new ArrayList<>();
    }
}
