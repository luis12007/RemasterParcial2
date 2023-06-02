package com.partialsimulation.partialsimulation.services;

import java.util.List;
import java.util.UUID;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.AddSongDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.SavePlaylistDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.ListDetailsDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SongShowDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.Song;

public interface PlaylistService {
	/*
	 * Create, findOneById, findAll, deleteWhereUserId, getallSumtime 
	*/
	
	MessageResultDTO save(SavePlaylistDTO savePlaylistDTO) throws Exception;
	MessageResultDTO addSongToPlaylist(UUID playlistId, AddSongDTO addSongDTO) throws Exception;
	PlayList findOneById(String id);
	List<Song> findAllSongsWhereUserId(String identifier);
	List<PlayList> findAll();
	MessageResultDTO deleteById(String id) throws Exception;
	MessageResultDTO findAllPlaylist(String identifier);
	List<Song> findAllSongsWherePlaylistId(String identifier);
	ListDetailsDTO findDetailsPlaylist(UUID identifier);
}
