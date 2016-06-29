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

import soundlink.server.converter.AlbumDtoConverter;
import soundlink.server.converter.MusicDtoConverter;
import soundlink.server.dto.AlbumDto;
import soundlink.server.dto.MusicDto;
import soundlink.service.manager.AlbumManager;
import soundlink.service.manager.MusicManager;
import soundlink.service.manager.impl.FileManagerImpl;

@RestController
@RequestMapping("/soundlink")
public class SoundLinkController {

    @Autowired
    private FileManagerImpl fileManager;

    @Autowired
    private AlbumManager albumManager;

    @Autowired
    private MusicManager musicManager;

    @Autowired
    private AlbumDtoConverter albumDtoConverter;

    @Autowired
    private MusicDtoConverter musicDtoConverter;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping() {
        return "ok";
    }

    /**
     * Entry point to get all albums
     *
     * @return a Set with all albums informations
     */
    @RequestMapping(value = "/albums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<AlbumDto> getAlbums() {
        Set<AlbumDto> allAlbums = albumDtoConverter.convertToDtoSet(albumManager.getAllAlbums(), AlbumDto.class);
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
        Set<MusicDto> musicsFromAlbum = musicDtoConverter.convertToDtoSet(musicManager.getMusicsFromAlbum(albumId),
                MusicDto.class);
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
        return new FileSystemResource(fileManager.getFile(path + FileManagerImpl.separator + name + "." + ext));
    }
}
