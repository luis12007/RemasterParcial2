package com.partialsimulation.partialsimulation.controllers.playlist;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.AddSongDTO;
import com.partialsimulation.partialsimulation.models.dtos.playlist.SavePlaylistDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.ListDetailsDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.Song;
import com.partialsimulation.partialsimulation.services.PlaylistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @RequestMapping(value = "/playlist", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> CreatePlayList(@RequestBody @Valid SavePlaylistDTO savePlaylistDTO) throws Exception {
        MessageResultDTO messageResultDTO = playlistService.save(savePlaylistDTO);
        
        System.out.println(messageResultDTO.getMessage());

        switch (messageResultDTO.getMessage()){
            case "playlist añadida con éxito":{
                return new ResponseEntity<>(messageResultDTO.getMessage(),HttpStatus.OK);
            }        
            case "La playlist ya existe":{
                return new ResponseEntity<>(messageResultDTO.getMessage(),HttpStatus.CONFLICT);
            }
            default:{
                return new ResponseEntity<>(messageResultDTO.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/playlist/{code}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable UUID code, @RequestBody AddSongDTO addSongDTO) throws Exception {
        System.out.println(addSongDTO.getIdentifier());
        MessageResultDTO messageResultDTO = playlistService.addSongToPlaylist(code, addSongDTO);

        switch (messageResultDTO.getMessage()){
            case "canción añadida con éxito":{
                return new ResponseEntity<>(messageResultDTO.getMessage(),HttpStatus.OK);
            }        
            case "La canción ya existe en la playlist":{
                return new ResponseEntity<>(messageResultDTO.getMessage(),HttpStatus.CONFLICT);
            }
            default:{
                return new ResponseEntity<>(messageResultDTO.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
    }
    
    @RequestMapping(value = "/playlist/{code}", method = RequestMethod.GET)
    public ResponseEntity<?> addSongToPlaylist(@PathVariable UUID code) {
        ListDetailsDTO listdetails = playlistService.findDetailsPlaylist(code);

        if (listdetails == null) {
        	return new ResponseEntity<>("algo salio mal",HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>(listdetails,HttpStatus.OK);
       
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAllPlaylist(){
        return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findOneById(String id){

        PlayList playList = playlistService.findOneById(id);

        if(playList != null){
            return new ResponseEntity<>(playList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/findSongs/{identifier}", method = RequestMethod.POST)
    public ResponseEntity<?> findUserSongs(@PathVariable String identifier){
        List<Song> songs = playlistService.findAllSongsWhereUserId(identifier);

        if(songs == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/deletePlaylist/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteOneById(@PathVariable String id) throws Exception {
        MessageResultDTO messageResultDTO = playlistService.deleteById(id);

        switch (messageResultDTO.getMessage()){
            case "playlist eliminada con éxito":{
                return new ResponseEntity<>(HttpStatus.OK);
            }        
            case "No se pudo eliminar la playlist":{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            default:{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    

    @RequestMapping(value="/find/PlaylistSongs/{identifier}", method = RequestMethod.POST)
    public ResponseEntity<?> findPlaylistSongs(@PathVariable String identifier){
        List<Song> songs = playlistService.findAllSongsWherePlaylistId(identifier);

        if(songs == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }
}
