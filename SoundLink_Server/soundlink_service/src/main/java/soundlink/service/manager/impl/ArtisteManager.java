package soundlink.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.ArtisteRepository;
import soundlink.model.entities.Artiste;
import soundlink.service.manager.IArtisteManager;

/**
 * This class is used to manage artiste from repository
 *
 * @author xrobert
 *
 */
@Service
public class ArtisteManager implements IArtisteManager {

    @Autowired
    private ArtisteRepository artisteRepository;

    @Override
    public Artiste create(Artiste artiste) {
        artiste = artisteRepository.saveAndFlush(artiste);
        return artiste;
    }

    @Override
    public Artiste update(Artiste artiste) {
        return artisteRepository.save(artiste);
    }

    @Override
    public void delete(Artiste artiste) {
        artisteRepository.delete(artiste);
    }

    @Override
    public Artiste findOne(Integer key) {
        return artisteRepository.findOne(key);
    }

    @Override
    public Artiste getOne(Integer key) {
        return artisteRepository.getOne(key);
    }

    @Override
    public List<Artiste> getAll() {
        return artisteRepository.findAll();
    }

    @Override
    public List<Artiste> getAllArtistesHavingAlbum() {
        return artisteRepository.findAllHavingAlbum();
    }

    @Override
    public Artiste getArtisteByName(String artisteName) {
        return artisteRepository.findByName(artisteName);
    }

    @Override
    public List<Artiste> getArtistesStartWith(String startChain, Integer userId) {
        return artisteRepository.findByNameStartingWithIgnoreCase(startChain, userId);
    }

    @Override
    public List<Artiste> getArtistesByUserId(Integer userId) {
        return artisteRepository.getArtistesByUserId(userId);
    }
}
