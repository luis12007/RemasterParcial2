package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongsToFilterDTO {
	
	private String title;
	
	private String time;
	
	private Date date;
}
