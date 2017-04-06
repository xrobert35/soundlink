package soundlink.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.AlbumDto;
import soundlink.service.converter.AlbumDtoConverter;
import soundlink.service.manager.IAlbumManager;

@RestController
@RequestMapping("/soundlink")
public class AlbumsController {

    @Autowired
    private AlbumDtoConverter albumDtoConverter;

    @Autowired
    private IAlbumManager albumManager;

    /**
     * Entry point to get all albums
     *
     * @return a List with all albums informations
     */
    @RequestMapping(value = "/albums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AlbumDto> getAlbums() {
        List<AlbumDto> allAlbums = albumDtoConverter.convertToDtoList(albumManager.getAll());
        return allAlbums;
    }

    /**
     * Entry point to get all albums
     *
     * @return a List with all albums informations
     */
    @RequestMapping(value = "/albumsArtiste", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AlbumDto> getAlbumsArtiste(@RequestParam Integer artisteId) {
        List<AlbumDto> allAlbums = albumDtoConverter.convertToDtoList(albumManager.getAlbumFromArtiste(artisteId));
        return allAlbums;
    }

}
