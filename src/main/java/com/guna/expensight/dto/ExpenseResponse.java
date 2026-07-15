package com.guna.expensight.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private UserResponse user;
    private CategoryResponse category;
}
