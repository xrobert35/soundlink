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

    @Query("SELECT album " 
    + "FROM Album album "
//    + "WHERE album.valide = true "
    + "ORDER BY album.artiste.name, album.name ")
    List<Album> findAllOrderer();
    
    @Query("SELECT album " 
    + "FROM Album album "
//  + "WHERE album.valide = true "
    + "WHERE album.name like ':name%' "
    + "ORDER BY album.artiste.name, album.name ")
    List<Album> findValideAlbumsStartByNameOrderByName(@Param("name") String name);

    @Query("SELECT album " 
    + "FROM Album album "
    + "INNER JOIN album.artiste artiste "
//    + "WHERE album.valide = true "
    + "WHERE artiste.id = :artisteId "
    + "ORDER BY album.name ")
    List<Album> getAlbumsFromArtiste(@Param("artisteId") Integer artisteId);

    @Query("SELECT album " 
    + "FROM Album album "
    + "INNER JOIN album.users usersArtistes "
    + "INNER JOIN usersArtistes.user user "
    + "WHERE user.id = :userId "
//    + "WHERE album.valide = true "
    + "ORDER BY album.name ")
    List<Album> getAlbumsByUserId(@Param("userId") Integer userId);

}
