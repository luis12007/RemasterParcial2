package com.partialsimulation.partialsimulation.services.implementations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.AddSongDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.SavePlaylistDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.ListDetailsDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SongsFilterTimeDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SongsToFilterDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.SelectedSong;
import com.partialsimulation.partialsimulation.models.entities.Song;
import com.partialsimulation.partialsimulation.models.entities.User;
import com.partialsimulation.partialsimulation.repositories.PlaylistRepository;
import com.partialsimulation.partialsimulation.repositories.SelectedSongRepository;
import com.partialsimulation.partialsimulation.repositories.SongRepository;
import com.partialsimulation.partialsimulation.repositories.UserRepository;
import com.partialsimulation.partialsimulation.services.PlaylistService;

@Service
public class PlaylistImpl implements PlaylistService {

	@Autowired
	private	PlaylistRepository playlistRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SongRepository songRepository;
	
	@Autowired
	private SelectedSongRepository selectedRepository;

	@Override
	public MessageResultDTO save(SavePlaylistDTO savePlaylistDTO) throws Exception {

		System.out.println(savePlaylistDTO);
		
		List<User> users = userRepository.findAll();

		User user = users.stream()
						.filter(e -> e.getEmail().equals(savePlaylistDTO.getIdentifier()) || 
							e.getUsername().equals(savePlaylistDTO.getIdentifier()))
								.findAny().orElse(null);
		
		if(user == null){
			return new MessageResultDTO("usuario no encontrado");
		}

		List<PlayList> userPlaylists = playlistRepository.findAll().stream()
				.filter(e -> e.getUser().getCode().equals(user.getCode())).toList();

		PlayList filterPlayList = userPlaylists.stream()
					.filter(p -> p.getTitle().equals(savePlaylistDTO.getTitle())).findAny().orElse(null);
		
		if(filterPlayList == null){

			PlayList playlist = new PlayList(
					savePlaylistDTO.getTitle(),
					savePlaylistDTO.getDescription(),
					user
					);
					
			System.out.println(playlist);
			
			playlistRepository.save(playlist);
			return new MessageResultDTO("playlist añadida con éxito");
		}

		return new MessageResultDTO("La playlist ya existe");	
}

	@Override
	public PlayList findOneById(String id) {

		UUID code = UUID.fromString(id);

		PlayList playlist = playlistRepository.findById(code).orElse(null);

		return playlist;
	}
	
	@Override
	public List<Song> findAllSongsWhereUserId(String identifier) {
		UUID code = UUID.fromString(identifier);

		User user = userRepository.findById(code).orElse(null);
		
		List<PlayList> userPlaylists = user.getPlaylists();

		List<SelectedSong> selectedSongs = new ArrayList<SelectedSong>();

		for (PlayList p: userPlaylists){
			for (SelectedSong song: p.getPlaylists()){
				selectedSongs.add(song);
			}
		}

		List<Song> songs = new ArrayList<Song>();

		for(SelectedSong s: selectedSongs){
			s.getSong();
			Optional<Song> song = songRepository.findById(s.getSong().getCode());
			songs.add(song.get());		
		}
		
		return songs;

	}

	@Override
	public List<PlayList> findAll() {
		return playlistRepository.findAll();
	}

	@Override
	public MessageResultDTO deleteById(String id) throws Exception {
		
		UUID code = UUID.fromString(id);

		playlistRepository.deleteById(code);

		PlayList playList = playlistRepository.findAll().stream()
								.filter(p -> p.getCode().equals(code)).findAny().orElse(null);

		if(playList == null){
			return new MessageResultDTO("playlist eliminada con éxito");
		}else{
			return new MessageResultDTO("No se pudo eliminar la playlist");
		}
	}
	
	@Override
	public MessageResultDTO findAllPlaylist(String identifier) {
		UUID code = UUID.fromString(identifier);
		
		List<PlayList> filteredPlayList = playlistRepository.findAll().stream()
											.filter(p -> p.getUser().getCode().equals(code))
											.toList();

		if(filteredPlayList.isEmpty()){
			return new MessageResultDTO("No se encontraron playlists");

		}else{
			return new MessageResultDTO("Se encontraron playlists asociadas al usuario");
		}
	}

	public List<Song> findAllSongsWherePlaylistId(String identifier) {
		UUID code = UUID.fromString(identifier);

		PlayList playlist = playlistRepository.findById(code).orElse(null);

		List<SelectedSong> selectedSongs = playlist.getPlaylists();

		List<Song> songs = new ArrayList<Song>();

		for(SelectedSong s: selectedSongs){
			s.getSong();
			Optional<Song> song = songRepository.findById(s.getSong().getCode());
			songs.add(song.get());		
		}
		
		return songs;
	}

	@Override
	public MessageResultDTO addSongToPlaylist(UUID playlistId, AddSongDTO addSongDTO) throws Exception {
		
		System.out.println(playlistId);
		System.out.println(addSongDTO.getIdentifier());
		
		List<PlayList> playlists = playlistRepository.findAll();
		List<Song> songs = songRepository.findAll();


		PlayList playlist = playlists.stream()
		.filter(e -> e.getCode().equals(playlistId))
		.findAny().orElse(null);
		
		System.out.println("playlist: " + playlist);

		if(playlist == null){
			return new MessageResultDTO("No se encontró la playlist");
		}

		Song song = songs.stream()
						.filter(e -> e.getCode().equals(addSongDTO.getIdentifier()))
						.findAny().orElse(null);
		System.out.println("song: " + song);
		
		if(song == null){
			return new MessageResultDTO("No se encontró la canción");
		}

		List<SelectedSong> selectedSongs = selectedRepository.findAll();
		
		List<SelectedSong> filteredPlaylists = selectedSongs.stream()
											.filter(e -> e.getPlaylist().getCode().equals(playlistId)).toList();

		SelectedSong userSong = filteredPlaylists.stream()
						.filter(e -> e.getSong().getCode().equals(addSongDTO.getIdentifier()))
						.findAny().orElse(null);
		
		if(userSong != null){
			return new MessageResultDTO("La canción ya existe en la playlist");
		}

		LocalDate localDate = LocalDate.now();
		SelectedSong selectedSong = new SelectedSong(
			Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
			song,
			playlist
		);

		selectedRepository.save(selectedSong);
		return new MessageResultDTO("Canción añadida con éxito");
	}

	@Override
	public ListDetailsDTO findDetailsPlaylist(UUID identifier) {
		System.out.println(identifier);
		
		List<SelectedSong> selectedSongs = selectedRepository.findAll().stream()
											.filter(e -> e.getPlaylist().getCode().equals(identifier))
											.toList();
		System.out.println(selectedSongs);
		
		List<SongsFilterTimeDTO> mysongs = selectedSongs.stream().map(e -> new SongsFilterTimeDTO(e.getSong().getCode(),e.getSong().getTitle(),e.getSong().getDuration(),e.getDate_added())).toList();
		System.out.println(mysongs);
		List<SongsToFilterDTO> listaDTO = new ArrayList<>();
		int counterduration = 0;
		
		 for (SongsFilterTimeDTO song : mysongs) {
	            int minutes = song.getTime();
	            counterduration = counterduration+minutes;
	            int seconds = 60/minutes;

	            String duracionFormato = String.format("%02d:%02d", minutes, seconds);

	            SongsToFilterDTO cancionDTO = new SongsToFilterDTO(
						song.getId_song(),
	            		song.getTitle(),
	            		duracionFormato,
	            		song.getDate());

	            listaDTO.add(cancionDTO);
	        }
		 
		 int minutes = counterduration;
         int seconds = 60/minutes;

         String finald = String.format("%02d:%02d", minutes, seconds);
		
		 List<PlayList> playlists = playlistRepository.findAll();
		 PlayList playlist = playlists.stream()
					.filter(e -> e.getCode().equals(identifier))
					.findAny().orElse(null);

		return new ListDetailsDTO(playlist.getCode(),playlist.getTitle(),playlist.getDescription(),
							playlist.getUser().getCode(), listaDTO,finald);
	} 
}
