package com.partialsimulation.partialsimulation.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.ChangeUserPasswordDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.CreateUserDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.FindIdDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.LoginUserDTO;
import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.User;
import com.partialsimulation.partialsimulation.repositories.PlaylistRepository;
import com.partialsimulation.partialsimulation.repositories.UserRepository;
import com.partialsimulation.partialsimulation.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserImpl implements UserService {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private PlaylistRepository playlistrepo;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public MessageResultDTO save(CreateUserDTO createUserDTO) throws Exception {
		User newuser = new User(
				createUserDTO.getUsername(),
				createUserDTO.getEmail(),
				createUserDTO.getPassword());
		
		List<User> users = userrepo.findAll();
		
		User compuser = users.stream().filter(e -> (e.getUsername().equals(newuser.getUsername())))
				.findAny()
				.orElse(null);
				if(compuser != null) {
					if(compuser.getUsername().equals(newuser.getUsername()))
						return new MessageResultDTO("nombre de usuario no disponible");
		
				}	
				System.out.println(createUserDTO);
		userrepo.save(newuser);
		return new MessageResultDTO("usuario creado");
	}

	@Override
	public User findOneById(String id) {

		UUID code = UUID.fromString(id);
		
		Optional<User> useroptional = userrepo.findById(code);
		User user = useroptional.orElseThrow(() -> new RuntimeException("El objeto opcional está vacío"));
		
		return user;
	}

	

	@Override
	public List<User> findAll() {
		return userrepo.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public MessageResultDTO deleteById(String id) throws Exception {

			UUID code = UUID.fromString(id);

			userrepo.deleteById(code);
		return new MessageResultDTO("eliminado");
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public MessageResultDTO ChangePass(ChangeUserPasswordDTO changeUserPasswordDTO) throws Exception {
		User miEmpleado = findAll().stream()
				.filter(e -> ((e.getUsername().equals(changeUserPasswordDTO.getIdentifier()) ||
						e.getEmail().equals(changeUserPasswordDTO.getIdentifier())) &&
						e.getPassword().equals(changeUserPasswordDTO.getPassword())))
				.findAny()
				.orElse(null);
		if(miEmpleado != null) {
			miEmpleado.setPassword(changeUserPasswordDTO.getNewPassword());
		return new MessageResultDTO("cambiado correctamente");
		}
		
		return new MessageResultDTO("error campos no coinciden");
	}

	@Override
	public MessageResultDTO logIn(LoginUserDTO login) {
		
		List<User> users = userrepo.findAll();
		
		User myuser = users.stream().filter(e -> ((e.getUsername().equals(login.getIdentifier()) ||
				e.getEmail().equals(login.getIdentifier())) &&
				e.getPassword().equals(login.getPassword())))
		.findAny()
		.orElse(null);
		
		if(myuser == null)
			return new MessageResultDTO("mala");

		return new MessageResultDTO("bienvenido");
	}

	@Override
	public User finduserAndPlaylistWithSongs(String id) {
		UUID code = UUID.fromString(id);

		List<User> users = userrepo.findAll();

		User user = users.stream().filter(e -> e.getCode().equals(code))
				.findAny()
				.orElse(null);

		if(user == null){
			throw new RuntimeException("El objeto opcional está vacío");
		}

		return user;

	}

	@Override
	public User findOneByUsername(String username) {
		User user = userrepo.findByUsername(username);

		if(user == null)
			throw new RuntimeException("El objeto opcional está vacío");
		else{			
			return user;
		}
	}

	@Override
	public User findOneByEmail(String email) {
		User user = userrepo.findByEmail(email);

		if(user == null){
			throw new RuntimeException("El objeto opcional está vacío");
		}else{
			return user;
		}
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByUsername(String username) {
		userrepo.deleteByUsername(username);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByEmail(String email) {
		userrepo.deleteByEmail(email);
	}

	@Override
	public List<PlayList> findPlaylistByIdentifer(FindIdDTO info) {
		List<User> users = userrepo.findAll();
		List<PlayList> playlistAll = playlistrepo.findAll(); 
		
		User UserMail = users.stream().filter(e -> (e.getEmail().equals(info.getIdentifier())))
				.findAny()
				.orElse(null);
		System.out.println(UserMail);
				if(UserMail != null) {
					
					List<PlayList> playlistone = playlistAll.stream().filter(e -> e.getUser().getCode().equals(UserMail.getCode())).collect(Collectors.toList());
					return playlistone.stream()
			                .filter(playlist -> playlist.getTitle().toLowerCase().contains(info.getFragment().toString().toLowerCase()))
			                .collect(Collectors.toList());
				}

		User UserUsername = users.stream().filter(e -> (e.getUsername().equals(info.getIdentifier())))
						.findAny()
						.orElse(null);
				if(UserUsername != null) {
					List<PlayList> playlistone = playlistAll.stream().filter(e -> e.getUser().getCode().equals(UserUsername.getCode())).collect(Collectors.toList());
					System.out.println(playlistone);
					return playlistone.stream()
			                .filter(playlist -> playlist.getTitle().toLowerCase().contains(info.getFragment().toString().toLowerCase()))
			                .collect(Collectors.toList());
				}
	
		return null;
	}

}
