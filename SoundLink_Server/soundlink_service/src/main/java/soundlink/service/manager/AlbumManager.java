package soundlink.service.manager;

import java.util.Set;

import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;

/**
 * Manager used to manage album in database
 *
 * @author xrobert
 *
 */
public interface AlbumManager {

	/**
	 * Get all albums from database
	 *
	 * @return
	 */
	Set<Album> getAllAlbums();

	/**
	 * Get all albums from an artiste
	 *
	 * @param artiste
	 * @return
	 */
	Set<Album> getAlbumsByArtiste(Artiste artiste);

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
