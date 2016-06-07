package soundlink.service;

import java.util.Set;

import soundlink.model.music.AlbumDescriptor;
import soundlink.model.music.MusicDescriptor;

public interface IAlbumExplorerService {
	
	/**
	 * Retrieve all albums from the datastore
	 *  
	 * @return set of albums from the datastore
	 */
	Set<AlbumDescriptor> getAllAlbums();

	/**
	 * Retrieve all musics from an album 
	 * @param artisteName name of the artistes
	 * @param albumName name of the albums
	 * 
	 * @return set of musics from the datastore
	 */
	Set<MusicDescriptor> getMusicsFromAlbum(String artisteName, String albumName);
	
	/**
	 * Retrieve all musics from an album 
	 * @param artisteName name of the artistes
	 * @param albumName name of the albums
	 * @param title music title
	 * @return set of musics from the datastore
	 */
	MusicDescriptor getMusic(String artisteName, String albumName, String title);

}
