package soundlink.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soundlink.model.entities.Album;

/**
 * Album repository
 *
 * @author xrobert
 *
 */
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Album findAlbumByNameAndArtisteName(String albumName, String artistename);

    @Query("SELECT album FROM Album album ORDER BY album.artiste.name, album.name")
    List<Album> findAllOrderer();

}
