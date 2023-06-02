package com.partialsimulation.partialsimulation.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.partialsimulation.partialsimulation.models.entities.PlayList;
import com.partialsimulation.partialsimulation.models.entities.User;

public interface UserRepository extends ListCrudRepository<User, UUID>{
	
	User findByUsername(String username);
	User findByEmail(String email);
	void deleteByUsername(String username);
	void deleteByEmail(String email);
	List<User> findAll();
	void deleteById(UUID id);
	Optional<User> findById(UUID id);
	/*@Query("SELECT up.playlist, cp.cancion.nombre FROM UsuarioPlaylist up " +
            "JOIN CancionPlaylist cp ON up.id = cp.usuarioPlaylist.id " +
            "WHERE up.usuario.id = :usuarioId")
    List<Object[]> findPlaylistAndCancionesByUsuario(@Param("usuarioId") UUID usuarioId);
	@Query()
    List<Playlist> obtenerValoresPorId(@Param("playlistId") Long playlistId);*/
	@Query(value="SELECT p FROM Playlist p WHERE p.id_user_code = :playlistId",nativeQuery = true)
	List<PlayList> findByCodeIsContaining(@Param("playlistId") UUID playlistId);
}
