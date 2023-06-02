package com.partialsimulation.partialsimulation.models.dtos.song;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongsFilterTimeDTO {

	private String title;
	
	private Integer time;
	
	private Date date;
}
