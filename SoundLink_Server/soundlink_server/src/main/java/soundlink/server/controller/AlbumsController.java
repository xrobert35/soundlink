package soundlink.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.AlbumDto;
import soundlink.model.entities.Album;
import soundlink.model.entities.Users;
import soundlink.service.converter.AlbumDtoConverter;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IUsersManager;

@RestController
@RequestMapping("/soundlink/albums")
public class AlbumsController {

    @Autowired
    private AlbumDtoConverter albumDtoConverter;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private IUsersManager usersManager;

    /**
     * Entry point to get all albums
     *
     * @return a List with all albums informations
     */
    @RequestMapping(value = "/fromArtiste", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AlbumDto> getAlbumsArtiste(@RequestParam Integer artisteId) {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return albumDtoConverter.convertToDtoList(albumManager.getAlbumFromArtiste(artisteId, user.getId()));
    }

    @RequestMapping(value = "/userAlbums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AlbumDto> getUserAlbums() {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Album> albums = albumManager.getAlbumsByUserId(user.getId());
        return albumDtoConverter.convertToDtoList(albums);
    }
}
