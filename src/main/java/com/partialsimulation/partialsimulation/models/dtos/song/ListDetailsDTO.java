package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListDetailsDTO {

	private UUID id_playlist;

	private String title;

	private String description;

	private UUID id_user;

	private List<SongsToFilterDTO> songs;
	
	private String time;
}
