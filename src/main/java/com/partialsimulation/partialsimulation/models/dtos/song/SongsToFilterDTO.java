package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongsToFilterDTO {

	private UUID id_song;
	
	private String title;
	
	private String time;
	
	private Date date;
}
