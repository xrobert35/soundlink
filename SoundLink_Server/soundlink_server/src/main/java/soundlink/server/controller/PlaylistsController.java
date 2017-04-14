package soundlink.server.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.PlaylistActionDto;
import soundlink.dto.PlaylistDto;
import soundlink.model.entities.Music;
import soundlink.model.entities.MusicsPlaylists;
import soundlink.model.entities.Playlist;
import soundlink.model.entities.Users;
import soundlink.model.entities.UsersPlaylists;
import soundlink.service.converter.PlaylistDtoConverter;
import soundlink.service.manager.IMusicManager;
import soundlink.service.manager.IPlaylistManager;
import soundlink.service.manager.IUsersManager;

@RestController
@RequestMapping("/soundlink/playlist")
public class PlaylistsController {

    @Autowired
    private PlaylistDtoConverter playlistDtoConverter;

    @Autowired
    private IPlaylistManager playlistManager;

    @Autowired
    private IMusicManager musicManager;

    @Autowired
    private IUsersManager usersManager;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PlaylistDto createPlayList(@RequestBody PlaylistDto playlistDto) {
        Playlist playlist = playlistDtoConverter.convertToEntity(playlistDto);
        Users creator = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        playlist.setCreateDate(LocalDateTime.now());
        playlist.setUpdateDate(LocalDateTime.now());
        playlist.setCreator(creator);

        UsersPlaylists usersPlaylists = new UsersPlaylists();
        usersPlaylists.setRelationDate(LocalDateTime.now());
        usersPlaylists.setPlaylist(playlist);
        usersPlaylists.setUser(creator);

        creator.getPlaylists().add(usersPlaylists);

        playlist = playlistManager.create(playlist);

        return playlistDtoConverter.convertToDto(playlist);
    }

    @RequestMapping(value = "/addMusic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addMusic(@RequestBody PlaylistActionDto playlistAction) {
        Playlist playlist = playlistManager.findOne(playlistAction.getPlaylistId());

        MusicsPlaylists musicsPlaylists = new MusicsPlaylists();
        musicsPlaylists.setRelationDate(LocalDateTime.now());
        musicsPlaylists.setPlaylist(playlist);

        Music music = musicManager.findOne(playlistAction.getMusicId());
        musicsPlaylists.setMusic(music);

        playlist.getMusics().add(musicsPlaylists);

        playlist.setUpdateDate(LocalDateTime.now());
        playlistManager.update(playlist);
    }

    @RequestMapping(value = "/removeMusic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void removeMusic(@RequestBody PlaylistActionDto playlistAction) {
        Playlist playlist = playlistManager.findOne(playlistAction.getPlaylistId());
        playlist.getMusics().removeIf(musicPlaylist -> {
            return musicPlaylist.getMusic().getId().equals(playlistAction.getMusicId());
        });
        playlist.setUpdateDate(LocalDateTime.now());
        playlistManager.update(playlist);
    }

    @RequestMapping(value = "/playlistsByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PlaylistDto> getPlaylistByUserId(@RequestParam Integer userId) {
        List<Playlist> playlists = playlistManager.getPlaylistsByUserId(userId);
        return playlistDtoConverter.convertToDtoList(playlists);
    }

    @RequestMapping(value = "/userPlaylists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PlaylistDto> getUserPlaylists() {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Playlist> playlists = playlistManager.getPlaylistsByUserId(user.getId());
        return playlistDtoConverter.convertToDtoList(playlists);
    }
}
