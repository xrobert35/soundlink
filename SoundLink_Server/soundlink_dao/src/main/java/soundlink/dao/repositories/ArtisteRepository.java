package soundlink.dao.repositories;

import java.util.List;

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
     + "WHERE lower(unaccent(artiste.name)) = lower(unaccent(:artisteName))")
    Artiste findByName(@Param("artisteName") String artisteName);

    @Query("SELECT artiste "
    + "FROM Artiste artiste "
    + "WHERE EXISTS (SELECT 1 FROM Album albumArtiste WHERE albumArtiste.artiste.id = artiste.id)")
    List<Artiste> findAllHavingAlbum();

    @Query("SELECT artiste "
    + "FROM Artiste artiste "
    + "WHERE lower(unaccent(artiste.name)) like lower(unaccent(:artisteName||'%')) "
    + "AND EXISTS (SELECT 1 FROM Album albumArtiste WHERE albumArtiste.artiste.id = artiste.id)")
    List<Artiste> findByNameStartingWithIgnoreCase(@Param("artisteName") String artisteName);
}
