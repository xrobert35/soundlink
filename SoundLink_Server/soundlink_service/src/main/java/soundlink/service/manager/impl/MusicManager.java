package soundlink.service.manager.impl;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.MusicRepository;
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

    private static final Logger LOGGER = LogManager.getLogger(AlbumManager.class);

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public List<Music> findAllById(List<Integer> musicIds) {
        return musicRepository.findAllById(musicIds);
    }

    @Override
    public Music create(Music music) {
        if (music.getAlbum() != null) {
            music = musicRepository.save(music);
            musicRepository.flush();
            return music;
        } else {
            throw new InvalidParameterException("You must specificy the album !");
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
    public Music update(Music music) {
        return musicRepository.save(music);
    }

    @Override
    public void delete(Music music) {
        musicRepository.delete(music);
    }

    @Override
    public Music findOne(Integer key) {
        return musicRepository.findOne(key);
    }

    @Override
    public Music getOne(Integer key) {
        return musicRepository.getOne(key);
    }

    @Override
    public List<Music> getAll() {
        return musicRepository.findAll();
    }

    @Override
    public List<Music> getMusicsFromAlbum(Integer albumId) {
        return musicRepository.getMusicsFromAlbum(albumId);
    }

    @Override
    public List<Music> getMusicsFromPlaylist(Integer playlistId) {
        return musicRepository.getMusicsFromPlaylist(playlistId);
    }

    @Override
    public File getMusicFile(Integer musicId) {
        Music music = musicRepository.findOne(musicId);
        return new File(soundlinkFolder + music.getMusicFilePath());
    }

    @Override
    public Music getMusicByTitleAlbumNameArtisteName(String title, String albumName, String artisteName) {
        return musicRepository.getMusicByTitleAlbumNameArtisteName(title, albumName, artisteName);
    }
}
