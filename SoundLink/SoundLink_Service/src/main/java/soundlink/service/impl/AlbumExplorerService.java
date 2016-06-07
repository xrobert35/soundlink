package soundlink.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.datastore.AlbumDataStore;
import soundlink.datastore.manager.DataStoreFileManager;
import soundlink.model.music.AlbumDescriptor;
import soundlink.model.music.MusicDescriptor;
import soundlink.service.IAlbumExplorerService;

@Service
public class AlbumExplorerService implements IAlbumExplorerService {

	@Autowired
	private AlbumDataStore albumDatastore;
	
	@Autowired
	private DataStoreFileManager dataStoreManager;
	
	@Override
	public Set<AlbumDescriptor> getAllAlbums() {
		return albumDatastore.getAlbums();
	}
	
	@Override
	public Set<MusicDescriptor> getMusicsFromAlbum(String artisteName, String albumName){
		AlbumDescriptor album = albumDatastore.getAlbum(artisteName, albumName);
		if(album != null){
			dataStoreManager.initAlbumMusics(album);
		}
		return album.getMusics();
	}

	@Override
	public MusicDescriptor getMusic(String artisteName, String albumName, String title) {
		AlbumDescriptor album = albumDatastore.getAlbum(artisteName, albumName);
		Set<MusicDescriptor> musics = album.getMusics();
		for(MusicDescriptor musicDescriptor : musics){
			if(musicDescriptor.getTitle().equals(title)){
				return musicDescriptor;
			}
		}
		return null;
	}
}
