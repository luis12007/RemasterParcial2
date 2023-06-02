package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListDetailsDTO {

	private List<SongsToFilterDTO> songs;
	
	private String time;
}
