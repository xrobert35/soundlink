package soundlink.service.manager.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(AlbumManager.class);

    @Autowired
    private ArtisteRepository artisteRepository;

    @Override
    public List<Artiste> getAllArtistes() {
        return artisteRepository.findAll();
    }

    @Override
    public Artiste create(Artiste artiste) {
        artiste = artisteRepository.save(artiste);
        artisteRepository.flush();
        return artiste;
    }

    @Override
    public boolean deleteArtiste(Artiste artiste) {
        if (artisteRepository.exists(artiste.getId())) {
            artisteRepository.delete(artiste.getId());
            return true;
        } else {
            LOGGER.info("Cannot delete a none existing album with id " + artiste.getId());
            return false;
        }
    }

    @Override
    public Artiste getArtisteByName(String artisteName) {
        return artisteRepository.findByName(artisteName);
    }
}