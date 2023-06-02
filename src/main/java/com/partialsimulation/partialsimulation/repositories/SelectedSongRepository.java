package com.partialsimulation.partialsimulation.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.partialsimulation.partialsimulation.models.dtos.song.SaveSelectedSongDTO;
import com.partialsimulation.partialsimulation.models.entities.SelectedSong;

public interface SelectedSongRepository extends ListCrudRepository<SelectedSong, UUID> {

	void save(SaveSelectedSongDTO entity);
	
	/*SelectedSong findByDateAdded(String dateAdded);
	SelectedSong deleteByDateAdded(String dateAdded);
	List<SelectedSong> findAllByDateAdded(String dateAdded);
	List<SelectedSong> deleteAllByDateAdded(String dateAdded);*/
	List<SelectedSong> findAll();
/* 	SelectedSong findByDateAdded(String dateAdded);
	SelectedSong deleteByDateAdded(String dateAdded);
	List<SelectedSong> findAllByDateAdded(String dateAdded);
	List<SelectedSong> deleteAllByDateAdded(String dateAdded); */
	void deleteById(UUID id);
	Optional<SelectedSong> findById(UUID id);
	List<SelectedSong> findByCodeContainingOrderByCodeAsc(UUID id);
	
}
