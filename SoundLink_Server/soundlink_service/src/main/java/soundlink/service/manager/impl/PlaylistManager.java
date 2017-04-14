package soundlink.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.PlaylistRepository;
import soundlink.model.entities.Playlist;
import soundlink.service.manager.IPlaylistManager;

@Service
public class PlaylistManager implements IPlaylistManager {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public Playlist create(Playlist playlist) {
        return playlistRepository.saveAndFlush(playlist);
    }

    @Override
    public Playlist update(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public void delete(Playlist playlist) {
        playlistRepository.delete(playlist);
    }

    @Override
    public Playlist findOne(Integer key) {
        return playlistRepository.findOne(key);
    }

    @Override
    public Playlist getOne(Integer key) {
        return playlistRepository.getOne(key);
    }

    @Override
    public List<Playlist> getAll() {
        return playlistRepository.findAll();
    }

    @Override
    public List<Playlist> getPlaylistsByUserId(Integer userId) {
        return playlistRepository.getPlaylistsFromUserId(userId);
    }
}
