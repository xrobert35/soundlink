package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soundlink.model.entities.Artiste;

/**
 * Artiste repository
 *
 * @author xrobert
 *
 */
public interface ArtisteRepository extends JpaRepository<Artiste, Integer> {

    /**
     * Allow you to find an artiste by is name
     *
     * @param name
     * @return
     */
    @Query("SELECT artiste "
    + "FROM Artiste artiste "
    + "WHERE upper(artiste.name) = upper(:artisteName)")
    Artiste findByName(@Param("artisteName") String artisteName);
}
