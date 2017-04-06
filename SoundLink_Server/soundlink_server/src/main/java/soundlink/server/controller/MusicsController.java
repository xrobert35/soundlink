package soundlink.server.controller;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.MusicDto;
import soundlink.server.utils.MultipartFileSender;
import soundlink.service.converter.MusicDtoConverter;
import soundlink.service.manager.IMusicManager;

@RestController
@RequestMapping("/soundlink")
public class MusicsController {

    @Autowired
    private IMusicManager musicManager;

    @Autowired
    private MusicDtoConverter musicDtoConverter;

    /**
     * Entry point to get all music from an album
     *
     * @param albumId id of the album
     * @return a set with all musics informations
     */
    @RequestMapping(value = "/albumMusics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<MusicDto> getAlbumMusics(@RequestParam Integer albumId) {
        if (albumId == null) {
            throw new InvalidParameterException("Album id cannot be null");
        }
        List<MusicDto> musicsFromAlbum = musicDtoConverter.convertToDtoList(musicManager.getMusicsFromAlbum(albumId));
        return musicsFromAlbum;
    }

    @RequestMapping(value = "/music/{musicId}", method = RequestMethod.GET)
    public void getEpisodeFile(@PathVariable("musicId") Integer id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        File file = musicManager.getMusicFile(id);
        MultipartFileSender.fromPath(file.toPath()).with(request).with(response).serveResource();
    }

}
