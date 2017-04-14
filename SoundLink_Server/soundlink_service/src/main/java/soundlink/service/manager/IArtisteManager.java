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
public interface IArtisteManager extends ISoundlinkManager<Artiste, Integer> {

    /**
     * Get all artistes having an album from database
     * 
     * @return
     */
    List<Artiste> getAllArtistesHavingAlbum();

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
     * @param userId
     * @return
     */
    List<Artiste> getArtistesStartWith(String startChain, Integer userId);

    /**
     * Get all artistes from user
     * 
     * @param user id
     * @return
     */
    List<Artiste> getArtistesByUserId(Integer userId);

}
