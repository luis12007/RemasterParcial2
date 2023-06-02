package com.partialsimulation.partialsimulation.models.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDTO {

    @NotEmpty(message = "the field 'identifier' should not be empty")
    private String identifier;

    @Pattern(regexp = "^[0-9a-z]{8,8}$", message = "repect regex expressions")
    @NotEmpty(message = "the field 'password' should not be empty")
    @Size(min=8, max=8, message = "respect the regulation length")
    private String password;
}
