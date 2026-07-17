package com.guna.expensight.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

	private Long id;
	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;

	private String password;
}
