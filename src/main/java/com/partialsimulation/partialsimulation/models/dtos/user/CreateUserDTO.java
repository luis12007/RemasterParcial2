package com.partialsimulation.partialsimulation.models.dtos.user;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

	@NotEmpty(message = "The field of 'username' shouldn't be empty")
	private String username;

	@NotEmpty(message = "The field of 'email' shouldn't be empty")
    private String email;
    
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%])[A-Za-z\\d@#$%]+$", message = "respect regex expressions")
	@NotEmpty(message = "The field of 'password' shouldn't be empty")
	@Size(min = 8, max = 8, message = "The field of 'password' should be 8 characters")
	private String password;
}
