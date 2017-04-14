package soundlink.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soundlink.model.entities.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("SELECT playlist "
    + "FROM Playlist playlist "
    + "INNER JOIN playlist.users usersArtistes "
    + "INNER JOIN usersArtistes.user user "
    + "WHERE user.id = :userId")
    List<Playlist> getPlaylistsFromUserId(@Param("userId") Integer userId);

}
