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
     + "WHERE upper(unaccent(artiste.name)) = upper(unaccent(:artisteName))")
    Artiste findByName(@Param("artisteName") String artisteName);

    @Query("SELECT artiste " 
            + "FROM Artiste artiste " 
            + "WHERE EXISTS (SELECT 1 FROM Album albumArtiste WHERE albumArtiste.artiste.id = artiste.id)")
    List<Artiste> findAllHavingAlbum();
    
    List<Artiste> findByNameStartingWith(String artisteName);
}
