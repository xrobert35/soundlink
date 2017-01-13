package soundlink.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soundlink.model.entities.Album;

/**
 * Album repository
 *
 * @author xrobert
 *
 */
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    @Query("SELECT album FROM Album album "
    + "INNER JOIN album.artiste artiste "
    + "WHERE upper(album.name) = upper(:albumName) "
    + "AND upper(artiste.name) = upper(:artisteName) ")
    Album findAlbumByNameAndArtisteName(@Param("albumName") String albumName, @Param("artisteName") String artistename);

    @Query("SELECT album FROM Album album ORDER BY album.artiste.name, album.name")
    List<Album> findAllOrderer();

}
