package com.budgetsync.budgetsync.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; 

    @Column(nullable = false)
    private Long budgetId; 

    @Column(nullable = false)
    private Double amount; 

    @Column(nullable = false, updatable = false)
    private LocalDateTime date = LocalDateTime.now();

}
