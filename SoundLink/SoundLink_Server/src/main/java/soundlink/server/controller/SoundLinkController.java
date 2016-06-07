package soundlink.server.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.model.music.AlbumDescriptor;
import soundlink.model.music.MusicDescriptor;
import soundlink.service.IAlbumExplorerService;
import soundlink.service.impl.FileManager;

@RestController
@RequestMapping("/soundlink")
public class SoundLinkController {

	@Autowired
	private FileManager fileManager;

	@Autowired
	private IAlbumExplorerService albumExplorer;

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "ok";
	}

	@RequestMapping(value = "/albums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Set<AlbumDescriptor> getAlbums() {
		Set<AlbumDescriptor> allAlbums = albumExplorer.getAllAlbums();
		return allAlbums;
	}

	@RequestMapping(value = "/albumMusics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Set<MusicDescriptor> getAlbumMusics(@RequestParam String artisteName, @RequestParam String albumName) {
		Set<MusicDescriptor> musicsFromAlbum = albumExplorer.getMusicsFromAlbum(artisteName, albumName);
		return musicsFromAlbum;
	}

	@RequestMapping(value = "/music/{artiste}/{album}/{title}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource getMusicStream(@PathVariable String artiste, @PathVariable String album,
			@PathVariable String title) {
		return new FileSystemResource(albumExplorer.getMusic(artiste, album, title).getFile());
	}

	@RequestMapping(value = "/image/{path}/{name}.{ext}", produces = MediaType.IMAGE_PNG_VALUE)
	public FileSystemResource showImage(@PathVariable("path") String path, @PathVariable("name") String name,
			@PathVariable("ext") String ext) {
		return new FileSystemResource(fileManager.getFile(path + FileManager.separator + name + "." + ext));
	}
}
