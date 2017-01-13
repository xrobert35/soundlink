package soundlink.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soundlink.model.entities.Music;

/**
 * Music repository
 *
 * @author xrobert
 *
 */
public interface MusicRepository extends JpaRepository<Music, Integer> {

    @Query("SELECT music FROM Music music " 
    + "INNER JOIN FETCH music.album album "
    + "INNER JOIN FETCH album.artiste artiste " 
    + "WHERE music.album.id = :albumId "
    + "ORDER BY music.discNumber, music.trackNumber ASC")
    List<Music> getMusicsFromAlbum(@Param("albumId") Integer albumId);

    @Query("SELECT music FROM Music music "
    + "INNER JOIN music.album album "
    + "INNER JOIN album.artiste artiste "
    + "WHERE upper(music.title) = upper(:title) "
    + "AND upper(album.name) = upper(:albumName) "
    + "AND upper(artiste.name) = upper(:artisteName) ")
    Music getMusicByTitleAlbumNameArtisteName(@Param("title") String title, @Param("albumName") String albumName, @Param("artisteName") String artisteName);

}
