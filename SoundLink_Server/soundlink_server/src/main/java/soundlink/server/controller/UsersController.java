package soundlink.server.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.UsersDto;
import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Users;
import soundlink.model.entities.UsersAlbums;
import soundlink.model.entities.UsersArtistes;
import soundlink.service.converter.UsersDtoConverter;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IUsersManager;

@RestController
@RequestMapping("/soundlink/users")
public class UsersController {

    @Autowired
    private IUsersManager usersManager;

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private UsersDtoConverter usersDtoConveter;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/userInformation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UsersDto getUserInformation() {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return usersDtoConveter.convertToDto(user);
    }

    @RequestMapping(value = "/checkPassword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean checkUserPassword(@RequestParam("pwd") String pwd) {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return bCryptPasswordEncoder.matches(pwd, user.getPassword());
    }

    @RequestMapping(value = "/saveUserInformation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UsersDto saveUserInformation(@RequestBody UsersDto usersDto) {
        if (usersDto.getNewPassword() != null) {
            if (usersDto.getNewPassword().equals(usersDto.getConfirmPassword())) {
                if (usersDto.getCurrentPassword() == null) {
                    throw new IllegalArgumentException("Current password can't be empty");
                }
            } else {
                throw new IllegalArgumentException("Password can't be different");
            }
            usersDto.setNewPassword(bCryptPasswordEncoder.encode(usersDto.getNewPassword()));
        }
        Users user = usersManager.updateUser(SecurityContextHolder.getContext().getAuthentication().getName(),
                usersDto);
        return usersDtoConveter.convertToDto(user);
    }

    @RequestMapping(value = "/addFavoriteArtiste", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addFavoriteArtiste(@RequestParam("artisteId") Integer artisteId) {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Artiste artiste = artisteManager.findOne(artisteId);

        UsersArtistes usersArtistes = new UsersArtistes();
        usersArtistes.setUser(user);
        usersArtistes.setArtiste(artiste);
        usersArtistes.setRelationDate(LocalDateTime.now());

        user.getFavoritesArtistes().add(usersArtistes);

        usersManager.update(user);
    }

    @RequestMapping(value = "/removeFavoriteArtiste", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void removeFavoriteArtiste(@RequestParam("artisteId") Integer artisteId) {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        user.getFavoritesArtistes().removeIf(favoriteArtiste -> {
            return favoriteArtiste.getArtisteId().equals(artisteId);
        });
        usersManager.update(user);
    }

    @RequestMapping(value = "/addFavoriteAlbum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addFavoriteAlbum(@RequestParam("albumId") Integer albumId) {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Album album = albumManager.findOne(albumId);

        UsersAlbums usersAlbums = new UsersAlbums();
        usersAlbums.setUser(user);
        usersAlbums.setAlbum(album);
        usersAlbums.setRelationDate(LocalDateTime.now());

        user.getFavoritesAlbums().add(usersAlbums);

        usersManager.update(user);
    }

    @RequestMapping(value = "/removeFavoriteAlbum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void removeFavoriteAlbum(@RequestParam("albumId") Integer albumId) {
        Users user = usersManager.getUserWithFavoriteAlbumsFetchByLogin(
                SecurityContextHolder.getContext().getAuthentication().getName());
        user.getFavoritesAlbums().removeIf(favoriteAlbum -> {
            return favoriteAlbum.getAlbumId().equals(albumId);
        });
        usersManager.update(user);
    }
}
