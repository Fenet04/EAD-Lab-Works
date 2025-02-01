package com.budgetsync.budgetsync.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long adminId; 

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String timeFrame; 

    @Column(nullable = false)
    private Double budgetLimit; 

    @Column(nullable = false)
    private Double limitAmount; 

    @Column(nullable = false)
    private Double amount = 0.0; 

}
