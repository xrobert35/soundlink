package soundlink.service.manager.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.ArtisteRepository;
import soundlink.model.entities.Artiste;
import soundlink.service.manager.ArtisteManager;

/**
 * This class is used to manage artiste from repository
 *
 * @author xrobert
 *
 */
@Service
public class ArtisteManagerImpl implements ArtisteManager {

	private static final Logger LOGGER = Logger.getLogger(AlbumManagerImpl.class);

	@Autowired
	private ArtisteRepository artisteRepository;

	@Override
	public Set<Artiste> getAllArtistes() {
		return new HashSet<Artiste>(artisteRepository.findAll());
	}

	@Override
	public Artiste create(Artiste artiste) {
		return artisteRepository.save(artiste);
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
