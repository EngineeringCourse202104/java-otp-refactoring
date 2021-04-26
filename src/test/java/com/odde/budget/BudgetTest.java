package com.odde.budget;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MockRepository extends BudgetRepository {


    List<Budget> mockBudgets;

    public MockRepository(List<Budget> mockBudgets) {
        this.mockBudgets = mockBudgets;
    }

    @Override
    public List<Budget> findAll() {
        return mockBudgets;
    }
}

public class BudgetTest {

    @Test
    public void test_1_whole_budget() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget = new Budget();
            budget.amount = 31;
            budget.year = 2021;
            budget.month = 1;
            add(budget);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 31));

        assertEquals(31, result);
    }

    @Test
    public void test_1_whole_budget_none() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget = new Budget();
            budget.amount = 31;
            budget.year = 2021;
            budget.month = 2;
            add(budget);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 31));

        assertEquals(0, result);
    }

    @Test
    public void test_1_2_whole_budget() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget1 = new Budget();
            budget1.amount = 31;
            budget1.year = 2021;
            budget1.month = 1;
            add(budget1);

            Budget budget2 = new Budget();
            budget2.amount = 28;
            budget2.year = 2021;
            budget2.month = 2;
            add(budget2);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 2, 28));

        assertEquals(59, result);
    }

    @Test
    public void test_1_2_budget() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget1 = new Budget();
            budget1.amount = 31;
            budget1.year = 2021;
            budget1.month = 1;
            add(budget1);

            Budget budget2 = new Budget();
            budget2.amount = 28;
            budget2.year = 2021;
            budget2.month = 2;
            add(budget2);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 1, 15), LocalDate.of(2021, 2, 15));

        assertEquals(32, result);
    }

    @Test
    public void test_1_3_budget() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget1 = new Budget();
            budget1.amount = 31;
            budget1.year = 2021;
            budget1.month = 1;
            add(budget1);

            Budget budget2 = new Budget();
            budget2.amount = 28;
            budget2.year = 2021;
            budget2.month = 2;
            add(budget2);

            Budget budget3 = new Budget();
            budget3.amount = 31;
            budget3.year = 2021;
            budget3.month = 3;
            add(budget3);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 1, 15), LocalDate.of(2021, 3, 15));

        assertEquals(60, result);
    }

    @Test
    public void test_time_not_valid() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget1 = new Budget();
            budget1.amount = 31;
            budget1.year = 2021;
            budget1.month = 1;
            add(budget1);

            Budget budget2 = new Budget();
            budget2.amount = 28;
            budget2.year = 2021;
            budget2.month = 2;
            add(budget2);

            Budget budget3 = new Budget();
            budget3.amount = 31;
            budget3.year = 2021;
            budget3.month = 3;
            add(budget3);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 3, 15), LocalDate.of(2021, 1, 15));

        assertEquals(0, result);
    }


    @Test
    public void test_one_day() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget1 = new Budget();
            budget1.amount = 31;
            budget1.year = 2021;
            budget1.month = 1;
            add(budget1);

            Budget budget2 = new Budget();
            budget2.amount = 28;
            budget2.year = 2021;
            budget2.month = 2;
            add(budget2);

            Budget budget3 = new Budget();
            budget3.amount = 31;
            budget3.year = 2021;
            budget3.month = 3;
            add(budget3);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 3, 15), LocalDate.of(2021, 3, 15));

        assertEquals(1, result);
    }


    @Test
    public void test_1_20_budget() {

        List<Budget> list = new ArrayList<Budget>() {{
            Budget budget1 = new Budget();
            budget1.amount = 31;
            budget1.year = 2021;
            budget1.month = 1;
            add(budget1);

            Budget budget2 = new Budget();
            budget2.amount = 280;
            budget2.year = 2021;
            budget2.month = 2;
            add(budget2);
        }};

        BudgetModel budgetModel = new BudgetModel(new MockRepository(list));

        int result = budgetModel.queryBudget(LocalDate.of(2021, 1, 15), LocalDate.of(2021, 2, 15));

        assertEquals(167, result);
    }

}
