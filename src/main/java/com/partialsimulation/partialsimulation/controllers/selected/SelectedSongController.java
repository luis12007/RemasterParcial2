package com.partialsimulation.partialsimulation.controllers.selected;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.song.SaveSelectedSongDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.SelectedSong;
import com.partialsimulation.partialsimulation.models.entities.Song;
import com.partialsimulation.partialsimulation.services.implementations.SelectedSongImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/selected")
public class SelectedSongController {

	@Autowired
	private SelectedSongImpl selectedsongimpl;
	
	@GetMapping("/all")
	public ResponseEntity<?> findAllUser(){
		return new ResponseEntity<>(selectedsongimpl.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable UUID id){
		SelectedSong selected = selectedsongimpl.findOneById(id);
		if (selected != null) {
			return new ResponseEntity<>(selected,HttpStatus.OK); 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/asc/{id}")
	public ResponseEntity<?> findAllByPlaylistIdASC(@PathVariable UUID id){
		List<SelectedSong> selected = selectedsongimpl.findAllByPlaylistIdASC(id);
		if (selected != null) {
			return new ResponseEntity<>(selected,HttpStatus.OK); 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> createUser(@RequestBody @Valid SaveSelectedSongDTO saveSelectedSongDTO, Song song, PlayList playlist) throws Exception{
		MessageResultDTO myuser = selectedsongimpl.save(saveSelectedSongDTO,song,playlist);
		switch (myuser.getMessage()) {
		case "Creado": {
			return new ResponseEntity<>("Usuario creado",HttpStatus.OK);
		}
		case "nombre de usuario no disponible":{
			return new ResponseEntity<>("nombre de usuario no disponible",HttpStatus.BAD_REQUEST);
			
			}
		default:
			return new ResponseEntity<>("Los campos no son validos",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable UUID id) throws Exception{
		MessageResultDTO result = selectedsongimpl.deleteById(id);
		if(result.getMessage().equals("eliminado")) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
