package com.partialsimulation.partialsimulation.services;

import java.util.List;

import com.partialsimulation.partialsimulation.models.dtos.MessageResultDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.ChangeUserPasswordDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.CreateUserDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.LoginUserDTO;
import com.partialsimulation.partialsimulation.models.dtos.user.ShowInfoPlaylistDTO;
import com.partialsimulation.partialsimulation.models.entities.User;

public interface UserService {
	/*
	 * Create, findOneById, findAllSongsWhereUserId, findAll, deleteWhereUserId, ChangePass,Login,findAllPlaylist
	 * 
	*/
	
	MessageResultDTO save(CreateUserDTO createUserDTO) throws Exception;
	User findOneByUsername(String username);
	User findOneByEmail(String email);
	void deleteOneByUsername(String username) throws Exception;
	void deleteOneByEmail(String email) throws Exception;
	User findOneById(String id);
	List<User> findAll();
	MessageResultDTO deleteById(String id) throws Exception;
	MessageResultDTO ChangePass(ChangeUserPasswordDTO changeUserPasswordDTO)  throws Exception;
	MessageResultDTO logIn(LoginUserDTO login) throws Exception;
	User finduserAndPlaylistWithSongs(String id);
	List<ShowInfoPlaylistDTO> findPlaylistByIdentifer(String idUser, String identifier);
}
