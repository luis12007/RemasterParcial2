package com.partialsimulation.partialsimulation.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SaveSongDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SongShowDTO;
import com.partialsimulation.partialsimulation.models.entities.Song;
import com.partialsimulation.partialsimulation.repositories.SongRepository;
import com.partialsimulation.partialsimulation.services.SongService;

import jakarta.transaction.Transactional;

@Service
public class SongImpl implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public MessageResultDTO save(SaveSongDTO saveSongDTO) throws Exception {
		Song song = new	Song(
				saveSongDTO.getTitle(),
				saveSongDTO.getDuration()
				);

		List<Song> songs = songRepository.findAll();
		for (Song s : songs) {
			if (s.getTitle().equals(song.getTitle())) {
				throw new Exception("La canción ya existe");
				}
			}
			
			songRepository.save(song);
	
			return new MessageResultDTO("Canción añadida con éxito");
		}

	@Override
	public Song findOneById(String id) {
		UUID code = UUID.fromString(id);

		List<Song> songs = songRepository.findAll();

		Song song = songs.stream().filter(s -> s.getCode().equals(code)).findFirst().orElse(null);
		
		if(song == null){
			return null;
		}

		return song;

	}
	
	@Override
	public List<Song> findAll() {
		return	songRepository.findAll();
	}

	@Override
	public Song findOneByTitle(String title) {
		
		List<Song> songs = songRepository.findAll();

		Song song = songs.stream().filter(s -> s.getTitle().equals(title)).findFirst().orElse(null);

		if(song == null){
			return null;
		}

		return song;
	}

	@Override
	public MessageResultDTO deleteOneByTitle(String title) {
		songRepository.deleteByTitle(title);

		return new MessageResultDTO("canción eliminada");
	}


	@Override
	public void deleteAll() {
		songRepository.deleteAll();
	}

	@Override
	public MessageResultDTO deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);

		List<Song> songs = songRepository.findAll();

		Song song = songs.stream().filter(s -> s.getCode().equals(code)).findFirst().orElse(null);

		if(song == null){
			throw new Exception("The song doesn't exist");
		}

		songRepository.deleteById(code);

		return new MessageResultDTO("The song was deleted successfully");
	}

	@Override
	public Song findLongSong() {
			return songRepository.findLongSong();
	}

	@Override
	public List<SongShowDTO> finAllByTitlle(String filter) {
		List<Song> listsongs = songRepository.findByTitleIsContaining(filter);
		
		List<SongShowDTO> listaDTO = new ArrayList<>();
		
		 for (Song song : listsongs) {
	            int minutes = song.getDuration();
	            int seconds = 60/minutes;

	            String duracionFormato = String.format("%02d:%02d", minutes, seconds);

	            SongShowDTO cancionDTO = new SongShowDTO(
						song.getCode(),
	            		song.getTitle(),
	            		duracionFormato);

	            listaDTO.add(cancionDTO);
	        }

	        return listaDTO;
	}

}
