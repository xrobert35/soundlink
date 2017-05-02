package soundlink.server.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.playlist.CreatePlaylistDto;
import soundlink.dto.playlist.PlaylistActionDto;
import soundlink.dto.playlist.PlaylistDto;
import soundlink.model.entities.Music;
import soundlink.model.entities.MusicsPlaylists;
import soundlink.model.entities.Playlist;
import soundlink.model.entities.Users;
import soundlink.model.entities.UsersPlaylists;
import soundlink.service.constant.SoundlinkConstant;
import soundlink.service.converter.PlaylistDtoConverter;
import soundlink.service.manager.IMusicManager;
import soundlink.service.manager.IPlaylistManager;
import soundlink.service.manager.IUsersManager;
import soundlink.service.utils.ImageUtils;

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

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PlaylistDto createPlayList(@RequestBody @Valid CreatePlaylistDto createPlaylistDto) {
        Playlist playlist = new Playlist();
        playlist.setName(createPlaylistDto.getName());

        Users creator = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        playlist.setCreateDate(LocalDateTime.now());
        playlist.setUpdateDate(LocalDateTime.now());
        playlist.setCreator(creator);

        UsersPlaylists usersPlaylists = new UsersPlaylists();
        usersPlaylists.setRelationDate(LocalDateTime.now());
        usersPlaylists.setPlaylist(playlist);
        usersPlaylists.setUser(creator);

        creator.getPlaylists().add(usersPlaylists);

        final Playlist createdPlaylist = playlistManager.create(playlist);

        if (createPlaylistDto.getCover() != null) {
            try {
                byte[] playlistCoverByte = ImageUtils.reduceAndCreateJpgImage(createPlaylistDto.getCover(), 120, 120,
                        true);
                File playlistCover = new File(
                        soundlinkFolder + SoundlinkConstant.PLAYLISTS_COVERS_FOLDER + playlist.getId());
                FileUtils.writeByteArrayToFile(playlistCover, playlistCoverByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (CollectionUtils.isNotEmpty(createPlaylistDto.getMusicsId())) {
            List<Music> musics = musicManager.findAllById(createPlaylistDto.getMusicsId());
            musics.forEach(music -> {
                MusicsPlaylists musicPlaylist = new MusicsPlaylists();
                musicPlaylist.setMusic(music);
                musicPlaylist.setPlaylist(createdPlaylist);
                musicPlaylist.setRelationDate(LocalDateTime.now());
                playlist.getMusics().add(musicPlaylist);
            });
        }

        playlistManager.update(createdPlaylist);

        return playlistDtoConverter.convertToDto(createdPlaylist);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deletePlayList(@RequestParam Integer playlistId) {
        Playlist playlist = playlistManager.findOne(playlistId);
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        user.getPlaylists().removeIf(userPlaylist -> {
            return userPlaylist.getPlaylistId().equals(playlist.getId());
        });
        playlist.getUsers().removeIf(userPlaylist -> {
            return userPlaylist.getUserId().equals(user.getId());
        });
        usersManager.update(user);
        if (CollectionUtils.isEmpty(playlist.getUsers())) {
            playlistManager.delete(playlist);
        }
    }

    @RequestMapping(value = "/addMusic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addMusic(@RequestBody @Valid PlaylistActionDto playlistAction) {
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
    public void removeMusic(@RequestBody @Valid PlaylistActionDto playlistAction) {
        Playlist playlist = playlistManager.findOne(playlistAction.getPlaylistId());
        playlist.getMusics().removeIf(musicPlaylist -> {
            return musicPlaylist.getMusicId().equals(playlistAction.getMusicId());
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
