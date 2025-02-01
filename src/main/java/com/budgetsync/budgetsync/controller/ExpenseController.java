package com.budgetsync.budgetsync.controller;

import com.budgetsync.budgetsync.entity.Expense;
import com.budgetsync.budgetsync.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/log")
    public ResponseEntity<Expense> logExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.logExpense(expense));
    }

    @GetMapping("/user/{adminId}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long adminId) {
        return ResponseEntity.ok(expenseService.getExpensesByUser(adminId));
    }
    
    @GetMapping("/budget/{budgetId}")
    public ResponseEntity<List<Expense>> getExpensesByBudget(@PathVariable Long budgetId) {
        return ResponseEntity.ok(expenseService.getExpensesByBudget(budgetId));
    }
}
