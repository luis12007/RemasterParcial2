package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveSelectedSongDTO {

	@NotEmpty(message = "The field 'date_added' shouldn't be empty")
	private Date date_added;

	@NotEmpty(message = "The field 'song' shouldn't be empty")
    private String id_song;

	@NotEmpty(message = "The field 'playlist' shouldn't be empty")
    private String id_playlist;
}
