package soundlink.service.manager.impl;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.AlbumRepository;
import soundlink.dao.repositories.MusicRepository;
import soundlink.model.entities.Album;
import soundlink.model.entities.Music;
import soundlink.service.manager.IMusicManager;

/**
 * This class is used to manage musics from repository
 *
 * @author xrobert
 *
 */
@Service
public class MusicManager implements IMusicManager {

    private static final Logger LOGGER = Logger.getLogger(AlbumManager.class);

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Music> getMusicsFromAlbum(Integer albumId) {
        return musicRepository.getMusicsFromAlbum(albumId);
    }

    @Override
    public Music create(Music music) {
        if (music.getAlbum() != null) {
            Album albumMusic = albumRepository.findOne(music.getAlbum().getId());
            albumMusic.getMusics().add(music);
            albumRepository.save(albumMusic);
            return music;
        } else {
            throw new InvalidParameterException("You must specificy the album artiste !");
        }
    }

    @Override
    public boolean deleteMusic(Music music) {
        if (musicRepository.exists(music.getId())) {
            musicRepository.delete(music.getId());
            return true;
        } else {
            LOGGER.debug("Cannot find music to delete with id " + music.getId());
            return false;
        }
    }

    @Override
    public File getMusicFile(Integer musicId) {
        Music music = musicRepository.findOne(musicId);
        return new File(music.getMusicFilePath());
    }

    @Override
    public Music getMusicByTitle(String title) {
        return musicRepository.findMusicByTitle(title);
    }

}
