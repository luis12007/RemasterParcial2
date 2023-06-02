package com.partialsimulation.partialsimulation.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.partialsimulation.partialsimulation.models.dtos.song.SaveSongDTO;
import com.partialsimulation.partialsimulation.models.entities.Song;

public interface SongRepository extends JpaRepository<Song, UUID> {
	
		@Query("SELECT s FROM Song s ORDER BY s.duration DESC LIMIT 1")
		Song findLongSong();
        List<Song> findAll();
        Optional<Song> findById(UUID identifier);
        Song findByTitle(String title);
        void deleteByTitle(String title);
        List<Song> findAllByTitle(String title);
        void deleteById(UUID identifier);
        void deleteAll();
        void save(SaveSongDTO song);
        List<Song> findByTitleIsContaining(String title);
        
}   
