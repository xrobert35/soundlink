package soundlink.service.manager;

import java.io.File;
import java.util.Set;

import soundlink.model.entities.Music;

public interface MusicManager {

	/**
	 * Add all musics of an album
	 *
	 * @param album
	 * @return
	 */
	Set<Music> getMusicsFromAlbum(Long albumId);

	/**
	 * Add a music
	 *
	 * @param album
	 * @return
	 */
	Music create(Music music);

	/**
	 * Delete a music
	 *
	 * @param music
	 * @return
	 */
	boolean deleteMusic(Music music);

	/**
	 * This method is used to get the associated file from a music
	 *
	 * @param musicId
	 * @return
	 */
	File getMusicFile(Long musicId);

}
