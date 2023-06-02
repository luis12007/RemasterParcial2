package com.partialsimulation.partialsimulation.services;

import java.util.List;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SaveSongDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SongShowDTO;
import com.partialsimulation.partialsimulation.models.entities.Song;

public interface SongService {
	/*
	 * Create, findOneById, findAll, deleteWhereUserId, getallSumtime 
	*/
	
	MessageResultDTO save(SaveSongDTO saveSongDTO) throws Exception;
	Song findOneById(String id);
	Song findOneByTitle(String title);
	MessageResultDTO deleteOneByTitle(String title);
	void deleteAll();
	List<Song> findAll();
	MessageResultDTO deleteById(String id) throws Exception;
	Song findLongSong();
	List<SongShowDTO> finAllByTitlle(String filter);
}
