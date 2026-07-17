package com.guna.expensight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDTO {
	private Long id;
	@NotBlank(message = "Description is required")
	private String description;

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be greater than zero")
	private BigDecimal amount;

	@NotNull(message = "Date is required")
	private LocalDate date;

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotNull(message = "Category ID is required")
	private Long categoryId;
}
