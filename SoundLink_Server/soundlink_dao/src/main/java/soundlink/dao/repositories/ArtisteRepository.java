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
    + "LEFT JOIN artiste.users usersArtistes WITH usersArtistes.userId = :userId "
    + "WHERE lower(unaccent(artiste.name)) like lower(unaccent(:artisteName||'%')) "
    + "AND EXISTS (SELECT 1 FROM Album albumArtiste WHERE albumArtiste.artiste.id = artiste.id) "
    + "ORDER BY artiste.name  ASC ")
    List<Artiste> findByNameStartingWithIgnoreCase(@Param("artisteName") String artisteName, @Param("userId") Integer userId);
    
    @Query("SELECT artiste " 
    + "FROM Artiste artiste "
    + "INNER JOIN artiste.users usersArtistes "
    + "INNER JOIN usersArtistes.user user "
    + "WHERE user.id = :userId "
    + "ORDER BY artiste.name ")
    List<Artiste> getArtistesByUserId(@Param("userId") Integer userId);
}
