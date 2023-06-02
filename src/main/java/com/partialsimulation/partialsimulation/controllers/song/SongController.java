package com.partialsimulation.partialsimulation.controllers.song;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.FilterDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SaveSongDTO;
import com.partialsimulation.partialsimulation.models.entities.Song;
import com.partialsimulation.partialsimulation.services.SongService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;
    
    @PostMapping ("/")
    public ResponseEntity<?> saveSong(@RequestBody @Valid SaveSongDTO songDTO, BindingResult result){
        try {
			if (result.hasErrors()) {
				new ResponseEntity<MessageResultDTO>(
						new MessageResultDTO("No se pudo guardar la song" + result.getAllErrors().toString()),
						HttpStatus.BAD_REQUEST);
			}

			songService.save(songDTO);
			return new ResponseEntity<MessageResultDTO>(new MessageResultDTO("guardada con éxito."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MessageResultDTO>(new MessageResultDTO("Error interno."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
    }
    
    @GetMapping ("/")
    public ResponseEntity<?> findByFilter(@RequestBody @Valid FilterDTO filter){
    	System.out.println(filter);
			return new ResponseEntity<>(songService.finAllByTitlle(filter.getFilter()), HttpStatus.OK);
    }
    
    

    @GetMapping ("/long")
    public ResponseEntity<?> findLongSong() {
        
        try {
			Song song = songService.findLongSong();
			if (song == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Song>(song, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}


        
    }



    @RequestMapping(value = "/delete/id/{identifier}", method = RequestMethod.POST)
    public ResponseEntity<?> DeleteSongById(@PathVariable String identifier) throws Exception{
        
        MessageResultDTO message = songService.deleteById(identifier);

        if(message.getMessage() == "canción eliminada"){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> GetAllSongs(){
        return new ResponseEntity<>(songService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/title/{title}", method = RequestMethod.POST)
    public ResponseEntity<?> DeleteSongByTitle(@PathVariable String title) throws Exception{
        
        MessageResultDTO message = songService.deleteOneByTitle(title);

        if(message.getMessage() == "canción eliminada"){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/all/id/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<?> findOneSongById(@PathVariable String identifier)
    {   
        Song song = songService.findOneById(identifier);

        if(song == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> findOneSongByTitle(){

        Song song = songService.findOneByTitle("title");

        if(song == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/all", method = RequestMethod.POST)
    public ResponseEntity<?> DeleteAllSongs(){
        songService.deleteAll();

        if(songService.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus
            .OK);
        }
        else{
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }

    
}
