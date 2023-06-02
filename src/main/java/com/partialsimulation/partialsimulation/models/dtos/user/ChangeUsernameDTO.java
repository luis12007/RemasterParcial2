package com.partialsimulation.partialsimulation.models.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeUsernameDTO {

    @NotEmpty(message = "the field 'username' should not be empty")
    private String identifier;

    @NotEmpty(message = "the field 'username' should not be empty")
    private String newUsername;
}
