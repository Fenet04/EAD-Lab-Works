package com.budgetsync.budgetsync.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgetsync.budgetsync.entity.Budget;
import com.budgetsync.budgetsync.entity.User;
import com.budgetsync.budgetsync.security.JwtUtil;
import com.budgetsync.budgetsync.service.BudgetService;
import com.budgetsync.budgetsync.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final JwtUtil jwtUtil; 
    private final UserService userService; 

    @PostMapping("/create")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        System.out.println("Received budget request: " + budget);
        Budget savedBudget = budgetService.createBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBudget);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }


    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<Budget>> getBudgetsByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(budgetService.getBudgetsByAdmin(adminId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudget(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            String userEmail = jwtUtil.extractUsername(token.substring(7)); 
            User user = userService.getUserByEmail(userEmail);

            if (!"ADMIN".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can delete budgets");
            }

            budgetService.deleteBudget(id);
            return ResponseEntity.ok("Budget deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
