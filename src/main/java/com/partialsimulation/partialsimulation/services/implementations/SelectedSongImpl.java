package com.partialsimulation.partialsimulation.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SaveSelectedSongDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.SelectedSong;
import com.partialsimulation.partialsimulation.models.entities.Song;
import com.partialsimulation.partialsimulation.repositories.SelectedSongRepository;
import com.partialsimulation.partialsimulation.services.SelectedSongService;

import jakarta.transaction.Transactional;

@Service
public class SelectedSongImpl implements SelectedSongService {

	@Autowired
	private SelectedSongRepository selectedrepo;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public MessageResultDTO save(SaveSelectedSongDTO saveSelectedSongDTO, Song song, PlayList playlist) throws Exception {
		SelectedSong newselected = new SelectedSong(
				saveSelectedSongDTO.getDate_added(),
				song,
				playlist);
		
		selectedrepo.save(newselected);
		return new MessageResultDTO("movimiento guardado");
	}

	@Override
	public SelectedSong findOneById(UUID id) {
		
		Optional<SelectedSong> selectedoptional = selectedrepo.findById(id);
		SelectedSong selected = selectedoptional.orElseThrow(() -> new RuntimeException("El objeto opcional está vacío"));
		
		return selected;
	}

	@Override
	public List<SelectedSong> findAll() {
		// TODO validations
		return selectedrepo.findAll();
	}

	@Override
	public List<SelectedSong> findAllByPlaylistIdASC(UUID id) {
		// TODO validations
		return selectedrepo.findByCodeContainingOrderByCodeAsc(id);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public MessageResultDTO deleteById(UUID id) throws Exception {
		// TODO validations
		selectedrepo.deleteById(id);
		return new MessageResultDTO("deleted");
	}

	@Override
	public Integer getallSumtime(UUID id) {
		// TODO validations
		return null;
	}

}
