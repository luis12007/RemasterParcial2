package com.partialsimulation.partialsimulation.controllers.user;

import java.util.List;

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
import com.partialsimulation.partialsimulation.models.dtos.user.ChangeUserPasswordDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.CreateUserDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.FindIdDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.LoginUserDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.User;
import com.partialsimulation.partialsimulation.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/all")
	public ResponseEntity<?> findAllUser(){
		return new ResponseEntity<>(userservice.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable String id){
		User user = userservice.findOneById(id);
		if (user != null) {
			return new ResponseEntity<>(user,HttpStatus.OK); 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/playlist", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> findplaylistById(@RequestBody FindIdDTO info) throws Exception{
		List<PlayList> myplaylist = userservice.findPlaylistByIdentifer(info);
		if(myplaylist != null)
			return new ResponseEntity<>(myplaylist,HttpStatus.OK);
		
		return new ResponseEntity<>("algo salio mal",HttpStatus.OK);
	}
	
	@RequestMapping(value="/auth/signup", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) throws Exception{
		MessageResultDTO myuser = userservice.save(createUserDTO);
		switch (myuser.getMessage()) {
		case "usuario creado": {
			return new ResponseEntity<>("usuario creado",HttpStatus.OK);
		}
		case "nombre de usuario no disponible":{
			return new ResponseEntity<>("nombre de usuario no disponible",HttpStatus.BAD_REQUEST);
			
			}
		default:
			return new ResponseEntity<>("Los campos no son validos",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> singInUser(@RequestBody @Valid LoginUserDTO loginUserDTO) throws Exception{
		MessageResultDTO myuser = userservice.logIn(loginUserDTO);
		if(myuser.getMessage().equals("bienvenido")) {
			return new ResponseEntity<>("ingresando",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Los campos no son validos",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable String id) throws Exception{
		MessageResultDTO result = userservice.deleteById(id);
		if(result.getMessage().equals("eliminado")) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/changepass", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<?> changepass(@RequestBody @Valid ChangeUserPasswordDTO info) throws Exception{
		MessageResultDTO result = userservice.ChangePass(info);
		if(result.getMessage().equals("cambiado correctamente")) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value="/playlist", method = RequestMethod.GET)
    public ResponseEntity<?> findUserPlaylists(@RequestBody FindIdDTO identifier){
        
        List<PlayList> list = userservice.findPlaylistByIdentifer(identifier);
        if (list == null) {
        	return new ResponseEntity<>("error en campos",HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
	
	
	
}
