package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import soundlink.model.entities.Album;

/**
 * Album repository
 *
 * @author xrobert
 *
 */
public interface AlbumRepository extends JpaRepository<Album, Long> {

    // @Query("select alb from album alb inner join alb.artiste art where
    // alb.name = :albumname and art.name= :artistename")
    // Album findAlbumByNameAndArtisteName(@Param("albumname") String albumName,
    // @Param("artistename") String artistename);
    Album findAlbumByNameAndArtisteName(String albumName, String artistename);

}
