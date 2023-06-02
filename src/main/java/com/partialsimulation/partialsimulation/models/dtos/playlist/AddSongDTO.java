package com.partialsimulation.partialsimulation.models.dtos.playlist;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSongDTO {
    
    @NotEmpty(message = "The field 'identifier' shouldn't be empty")
    private UUID identifier;

}
