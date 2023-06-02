package com.partialsimulation.partialsimulation.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.AddSongDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.SavePlaylistDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.ListDetailsDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SongShowDTO;
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
	private SelectedSongRepository selectedrepository;

	@Override
	public MessageResultDTO save(SavePlaylistDTO savePlaylistDTO) throws Exception {

		System.out.println(savePlaylistDTO);
		
		List<User> users = userRepository.findAll();

		User user = users.stream()
						.filter(e -> e.getEmail().equals(savePlaylistDTO.getIdentifier()) || 
							e.getUsername().equals(savePlaylistDTO.getIdentifier()))
								.findAny().orElse(null);
		
		System.out.println(user);

		PlayList playlist = new PlayList(
				savePlaylistDTO.getTitle(),
				savePlaylistDTO.getDescription(),
				user
				);

		System.out.println(playlist);

		List<PlayList> playlists = playlistRepository.findAll();

		if (user == null) {
			return new MessageResultDTO("usuario no existe");
		}
		if(playlists.isEmpty()){
			playlistRepository.save(playlist);
			return new MessageResultDTO("playlist añadida con éxito");
		}
		
		PlayList filterPlayList = playlists.stream()
				.filter(e -> e.getTitle().equals(playlist.getTitle()))
				.findAny()
				.orElse(null);
		
		if(filterPlayList != null) {
			return new MessageResultDTO("playlist ya existe");
		}else{
			playlistRepository.save(playlist);
			return new MessageResultDTO("playlist añadida con éxito");
		}
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
		
		System.out.println("playlist:" + playlist);

		if(playlist == null){
			return new MessageResultDTO("No se encontró la playlist");
		}

		Song song = songs.stream()
						.filter(e -> e.getCode().equals(addSongDTO.getIdentifier()))
						.findAny().orElse(null);
		System.out.println("song:" + song);
		if(song == null){
			return new MessageResultDTO("No se encontró la canción");
		}

		List<SelectedSong> selectedSongs = playlist.getPlaylists();
		
		SelectedSong filterSong = selectedSongs.stream()
									.filter(e -> e.getSong().getCode().equals(song.getCode()))
									.findAny().orElse(null);
		System.out.println("filterSong:" + filterSong);
		if(filterSong != null){
			return new MessageResultDTO("La canción ya existe en la playlist");
		}else{
			SelectedSong selectedSong = new SelectedSong(null, new Date(),song, playlist);
			selectedrepository.save(selectedSong);
			return new MessageResultDTO("Canción añadida con éxito");
		}
		
	}

	@Override
	public ListDetailsDTO findDetailsPlaylist(UUID identifier) {

		List<SelectedSong> Selected = selectedrepository.findAll()
				.stream()
				.filter(e -> e.getPlaylist().getCode().equals(identifier))
				.collect(Collectors.toList());
		
		List<SongsFilterTimeDTO> mysongs = Selected.stream().map(e -> new SongsFilterTimeDTO(e.getSong().getTitle(),e.getSong().getDuration(),e.getDate_added())).collect(Collectors.toList());

		List<SongsToFilterDTO> listaDTO = new ArrayList<>();
		int counterduration = 0;
		
		 for (SongsFilterTimeDTO song : mysongs) {
	            int seconds = song.getTime();
	            counterduration = counterduration+seconds;
	            int minutes = seconds / 60;
	            int secondsShow = seconds % 60;

	            String duracionFormato = String.format("%02d:%02d", minutes, secondsShow);


	       
	            SongsToFilterDTO cancionDTO = new SongsToFilterDTO(
	            		song.getTitle(),
	            		duracionFormato,
	            		song.getDate());

	            listaDTO.add(cancionDTO);
	        }
		 
		 int seconds = counterduration;
         int minutes = seconds / 60;
         int secondsShow = seconds % 60;

         String finald = String.format("%02d:%02d", minutes, secondsShow);
		
		return new ListDetailsDTO(listaDTO,finald);
	} 
}
