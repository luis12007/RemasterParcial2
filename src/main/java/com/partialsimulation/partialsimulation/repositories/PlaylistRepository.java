package com.partialsimulation.partialsimulation.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.partialsimulation.partialsimulation.models.entities.PlayList;

public interface PlaylistRepository extends ListCrudRepository<PlayList, UUID>{
        List<PlayList> findAll();
        Optional<PlayList> findById(UUID identifier);
        void deleteById(UUID identifier);
        PlayList findBytitle(String title);
        void deleteByTitle(String title);
        void deleteAll();
        List<PlayList> findAllByTitle(String title);
        
        /*@Query("select s FROM song s INNER JOIN songxplaylist x ON s.code = x.id_song_code WHERE x.id_playlist_code = ':playlistId'")
        List<Song> findSongsByPlaylistIdWithDuration(@Param("playlistId") UUID playlistId);*/

}   
