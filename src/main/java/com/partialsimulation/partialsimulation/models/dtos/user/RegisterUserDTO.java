package com.partialsimulation.partialsimulation.models.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDTO {
    
    @NotEmpty(message = "the field 'username' should not be empty")
    @Size(min=6,max=10, message="respect the regulation length")
    private String username;

    @NotEmpty(message = "the field 'email' should not be empty")
    private String email;
    
    @Pattern(regexp = "^[0-9A-Z]{8}$", message = "repect regex expressions")
    @NotEmpty(message = "the field 'password' should not be empty")
    @Size(min=8, max=8, message = "respect the regulation length")
    private String password;

}
