package soundlink.server.controller;

import java.security.InvalidParameterException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.AlbumDto;
import soundlink.dto.MusicDto;
import soundlink.service.converter.AlbumDtoConverter;
import soundlink.service.converter.MusicDtoConverter;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IMusicManager;
import soundlink.service.manager.impl.FileManager;

@RestController
@RequestMapping("/soundlink")
public class SoundLinkController {

    @Autowired
    private FileManager fileManager;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private IMusicManager musicManager;

    @Autowired
    private AlbumDtoConverter albumDtoConverter;

    @Autowired
    private MusicDtoConverter musicDtoConverter;

    /**
     * Entry point to get all albums
     *
     * @return a Set with all albums informations
     */
    @RequestMapping(value = "/albums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<AlbumDto> getAlbums() {
        Set<AlbumDto> allAlbums = albumDtoConverter.convertToDtoSet(albumManager.getAllAlbums());
        return allAlbums;
    }

    /**
     * Entry point to get all music from an album
     *
     * @param albumId id of the album
     * @return a set with all musics informations
     */
    @RequestMapping(value = "/albumMusics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<MusicDto> getAlbumMusics(@RequestParam Long albumId) {
        if (albumId == null) {
            throw new InvalidParameterException("Album id cannot be null");
        }
        Set<MusicDto> musicsFromAlbum = musicDtoConverter.convertToDtoSet(musicManager.getMusicsFromAlbum(albumId));
        return musicsFromAlbum;
    }

    /**
     * Entry point to get the byte content of a music file
     *
     * @param musicId
     * @return the byte content of a music file
     */
    @RequestMapping(value = "/music/{musicId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getMusicStream(@PathVariable Long musicId) {
        return new FileSystemResource(musicManager.getMusicFile(musicId));
    }

    /**
     * Entry point used to get byte content of an image
     *
     * @param path image path
     * @param name image name
     * @param ext image extension
     * @return an image
     */
    @RequestMapping(value = "/image/{path}/{name}.{ext}", produces = MediaType.IMAGE_PNG_VALUE)
    public FileSystemResource showImage(@PathVariable("path") String path, @PathVariable("name") String name,
            @PathVariable("ext") String ext) {
        return new FileSystemResource(fileManager.getFile(path + FileManager.separator + name + "." + ext));
    }
}
