package com.budgetsync.budgetsync.service;

import com.budgetsync.budgetsync.entity.Budget;
import com.budgetsync.budgetsync.entity.Expense;
import com.budgetsync.budgetsync.repository.BudgetRepository;
import com.budgetsync.budgetsync.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    @Transactional
    public Expense logExpense(Expense expense) {
        Expense savedExpense = expenseRepository.save(expense);

        Budget budget = budgetRepository.findById(expense.getBudgetId())
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        
        budget.setAmount(budget.getAmount() + expense.getAmount());
        budgetRepository.save(budget);

        return savedExpense;
    }

    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getExpensesByBudget(Long budgetId) {
        return expenseRepository.findByBudgetId(budgetId);
    }
}
