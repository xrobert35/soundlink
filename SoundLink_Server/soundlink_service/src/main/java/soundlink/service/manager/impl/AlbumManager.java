package soundlink.service.manager.impl;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.AlbumRepository;
import soundlink.model.entities.Album;
import soundlink.service.manager.IAlbumManager;

/**
 * This classes is used to manage albums from repository
 *
 * @author xrobert
 *
 */
@Service
public class AlbumManager implements IAlbumManager {

    private static final Logger LOGGER = LogManager.getLogger(AlbumManager.class);

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAllOrderer();
    }

    @Override
    public Album create(Album album) {
        if (album.getArtiste() != null) {
            return albumRepository.save(album);
        } else {
            throw new InvalidParameterException("You must specificy the album artiste !");
        }
    }

    @Override
    public boolean deleteAlbum(Album album) {
        if (albumRepository.exists(album.getId())) {
            albumRepository.delete(album.getId());
            return true;
        } else {
            LOGGER.info("Cannot delete a none existing album with id " + album.getId());
            return false;
        }
    }

    @Override
    public Album findAlbumByNameAndArtisteName(String albumName, String artisteName) {
        return albumRepository.findAlbumByNameAndArtisteName(albumName, artisteName);
    }

    @Override
    public List<Album> getAlbumFromArtiste(Integer artisteId) {
        return albumRepository.getAlbumsFromArtiste(artisteId);
    }
}
