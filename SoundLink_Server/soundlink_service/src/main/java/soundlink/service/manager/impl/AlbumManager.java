package soundlink.service.manager.impl;

import java.security.InvalidParameterException;
import java.util.List;

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

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> getAll() {
        return albumRepository.findAllOrderer();
    }

    @Override
    public Album create(Album album) {
        if (album.getArtiste() != null) {
            return albumRepository.saveAndFlush(album);
        } else {
            throw new InvalidParameterException("You must specificy the album artiste !");
        }
    }

    @Override
    public void delete(Album album) {
        albumRepository.delete(album.getId());
    }

    @Override
    public Album update(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Album findOne(Integer key) {
        return albumRepository.findOne(key);
    }

    @Override
    public Album getOne(Integer key) {
        return albumRepository.getOne(key);
    }

    @Override
    public Album findAlbumByNameAndArtisteName(String albumName, String artisteName) {
        return albumRepository.findAlbumByNameAndArtisteName(albumName, artisteName);
    }

    @Override
    public List<Album> getAlbumFromArtiste(Integer artisteId, Integer userId) {
        return albumRepository.getAlbumsFromArtiste(artisteId, userId);
    }

    @Override
    public List<Album> getAlbumsByUserId(Integer userId) {
        return albumRepository.getAlbumsByUserId(userId);
    }
}
