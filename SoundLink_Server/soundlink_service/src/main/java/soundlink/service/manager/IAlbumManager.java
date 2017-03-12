package soundlink.service.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import soundlink.model.entities.Album;

/**
 * Manager used to manage album in database
 *
 * @author xrobert
 *
 */
@Transactional
public interface IAlbumManager {

    /**
     * Get all albums from database
     *
     * @return
     */
    List<Album> getAllAlbums();

    /**
     * Get all albums from artiste
     * 
     * @param artisteId
     * @return
     */
    List<Album> getAlbumFromArtiste(Integer artisteId);

    /**
     * Add a new album
     *
     * @param album
     * @return
     */
    Album create(Album album);

    /**
     * Delete an album
     *
     * @param album
     * @return
     */
    boolean deleteAlbum(Album album);

    /**
     * Find an album by his name and the artiste name
     * 
     * @param albumName
     * @param artisteName
     * @return
     */
    Album findAlbumByNameAndArtisteName(String albumName, String artisteName);

}
