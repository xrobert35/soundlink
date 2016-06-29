package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import soundlink.model.entities.Artiste;

/**
 * Artiste repository
 *
 * @author xrobert
 *
 */
public interface ArtisteRepository extends JpaRepository<Artiste, Long> {

    /**
     * Allow you to find an artiste by is name
     *
     * @param name
     * @return
     */
    Artiste findByName(String name);
}
