package com.guna.expensight.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardSummary {
    private long totalUsers;
    private long totalCategories;
    private long totalExpenses;
    private BigDecimal totalAmount;
}
