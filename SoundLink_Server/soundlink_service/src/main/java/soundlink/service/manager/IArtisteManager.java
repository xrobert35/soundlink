package soundlink.service.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import soundlink.model.entities.Artiste;

/**
 * Manager used to manage artiste in database
 *
 * @author xrobert
 *
 */
@Transactional
public interface IArtisteManager {

    /**
     * Get all artistes from database
     *
     * @return
     */
    List<Artiste> getAllArtistes();

    /**
     * Get all artistes having an album from database
     * 
     * @return
     */
    List<Artiste> getAllArtistesHavingAlbum();

    /**
     * Add a new artiste
     *
     * @param album
     * @return
     */
    Artiste create(Artiste artiste);

    /**
     * Update an artiste information
     * 
     * @param artiste
     */
    void update(Artiste artiste);

    /**
     * Delete an artiste
     *
     * @param album
     * @return
     */
    boolean deleteArtiste(Artiste artiste);

    /**
     * Get an artiste by his name
     *
     * @param artisteName
     */
    Artiste getArtisteByName(String artisteName);

    /**
     * Get all artistes starting with the specified chain
     * 
     * @param startChain
     * @return
     */
    List<Artiste> getArtistesStartWith(String startChain);

}
