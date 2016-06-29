package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import soundlink.model.entities.Music;

/**
 * Music repository
 *
 * @author xrobert
 *
 */
public interface MusicRepository extends JpaRepository<Music, Long> {

}
