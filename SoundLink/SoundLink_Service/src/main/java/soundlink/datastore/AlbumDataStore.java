package soundlink.datastore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import set.SearchSet;
import soundlink.model.music.AlbumDescriptor;

/**
 * Album datastore
 * 
 * @author xrobert
 */
@Component
public final class AlbumDataStore {

	private SearchSet<AlbumDescriptor> albums = new SearchSet<AlbumDescriptor>();

	public synchronized Set<AlbumDescriptor> getAlbums() {
		return new HashSet<AlbumDescriptor>(albums);
	}

	public synchronized void addAlbum(AlbumDescriptor album) {
		albums.add(album);
	}

	public synchronized AlbumDescriptor getAlbum(String artisteName, String albumName) {
		final AlbumDescriptor albumDescriptor = new AlbumDescriptor();
		albumDescriptor.setArtisteName(artisteName);
		albumDescriptor.setAlbumName(albumName);
		System.out.println("Xavier_Jaime_La_Fessée");
		return albums.get(albumDescriptor);
	}
}
