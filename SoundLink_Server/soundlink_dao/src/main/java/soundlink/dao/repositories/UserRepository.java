package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soundlink.model.entities.Users;

/**
 * User repository
 *
 * @author xrobert
 *
 */
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByLogin(String login);
    
    @Query("SELECT user "
    + "FROM Users user "
    + "LEFT JOIN user.favoritesArtistes "
    + "WHERE user.login = :login")
    Users getUserWithFavoriteArtistesFetchByLogin(@Param("login") String login);

    
    @Query("SELECT user "
    + "FROM Users user "
    + "INNER JOIN FETCH user.favoritesAlbums "
    + "WHERE user.login = :login")
    Users getUserWithFavoriteAlbumsFetchByLogin(@Param("login") String login);
}
