package com.partialsimulation.partialsimulation.models.dtos.song;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveSongDTO {

	@NotEmpty(message="the field 'title' shouldn´t be empty")
	private String title;
    
    private int duration;
}
