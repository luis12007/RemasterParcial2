package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongShowDTO {

	private UUID code;
	
	private String title;
	
	private String time;
}
