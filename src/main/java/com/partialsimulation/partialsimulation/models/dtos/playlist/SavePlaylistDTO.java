package com.partialsimulation.partialsimulation.models.dtos.playlist;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavePlaylistDTO {

	@NotEmpty(message = "The field 'title' shouldn't be empty")
	private String title;
    
	@NotEmpty(message = "The field 'description' shouldn't be empty")
    private String description;
    
	@NotEmpty(message = "The field 'identifier' shouldn't be empty")
    private String identifier;
}
