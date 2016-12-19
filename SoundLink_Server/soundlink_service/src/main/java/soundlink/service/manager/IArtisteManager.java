package soundlink.service.manager;

import java.util.Set;

import soundlink.model.entities.Artiste;

/**
 * Manager used to manage artiste in database
 *
 * @author xrobert
 *
 */
public interface IArtisteManager {

	/**
	 * Get all artistes from database
	 *
	 * @return
	 */
	Set<Artiste> getAllArtistes();

	/**
	 * Add a new artiste
	 *
	 * @param album
	 * @return
	 */
	Artiste create(Artiste artiste);

	/**
	 * Delete an artiste
	 *
	 * @param album
	 * @return
	 */
	boolean deleteArtiste(Artiste artiste);

	/**
	 * Get an artise by his name
	 *
	 * @param artisteName
	 */
	Artiste getArtisteByName(String artisteName);

}
