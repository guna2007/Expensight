package com.guna.expensight.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
	private Long id;
	@NotBlank(message = "Category name is required")
	private String name;
}
