package com.partialsimulation.partialsimulation.services;

import java.util.List;
import java.util.UUID;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SaveSelectedSongDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.SelectedSong;
import com.partialsimulation.partialsimulation.models.entities.Song;

public interface SelectedSongService {
	/*
	 * Create, findOneById, findAllWhereUserID, findAll, deleteWhereUserId, getallSumtime 
	 * 
	*/
	MessageResultDTO save(SaveSelectedSongDTO saveSelectedSongDTO, Song song, PlayList playlist) throws Exception;
	SelectedSong findOneById(UUID id);
	List<SelectedSong> findAll();
	List<SelectedSong> findAllByPlaylistIdASC(UUID id);
	MessageResultDTO deleteById(UUID id) throws Exception;
	Integer getallSumtime(UUID id);
}
