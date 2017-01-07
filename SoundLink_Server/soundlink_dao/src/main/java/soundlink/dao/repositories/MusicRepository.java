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

    @Query("SELECT music FROM Music music WHERE music.title = :title ")
    Music findMusicByTitle(@Param("title") String title);

    @Query("SELECT music FROM Music music " 
    + "INNER JOIN FETCH music.album album "
    + "INNER JOIN FETCH album.artiste artiste " 
    + "WHERE music.album.id = :albumId ORDER BY music.trackNumber")
    List<Music> getMusicsFromAlbum(@Param("albumId") Integer albumId);

}
