package com.budgetsync.budgetsync.service;

import com.budgetsync.budgetsync.entity.Budget;
import com.budgetsync.budgetsync.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Budget createBudget(Budget budget) {
    	System.out.println("Saving budget: " + budget);
        Budget savedBudget = budgetRepository.save(budget);
        System.out.println("Saved budget: " + savedBudget);
        return savedBudget;
    }
    
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }


    public List<Budget> getBudgetsByAdmin(Long adminId) {
        return budgetRepository.findByAdminId(adminId);
    }

    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
