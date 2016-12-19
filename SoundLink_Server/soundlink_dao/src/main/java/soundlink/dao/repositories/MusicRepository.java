package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soundlink.model.entities.Music;

/**
 * Music repository
 *
 * @author xrobert
 *
 */
public interface MusicRepository extends JpaRepository<Music, Long> {

    @Query("SELECT music FROM Music music WHERE music.title = :title ")
    Music findMusicByTitle(String title);

}
