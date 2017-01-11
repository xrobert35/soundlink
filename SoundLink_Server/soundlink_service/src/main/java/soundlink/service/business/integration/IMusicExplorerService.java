package soundlink.service.business.integration;

import soundlink.dto.IntegrationDto;

/**
 * Service used to explore music files
 *
 * @author xrobert
 *
 */
public interface IMusicExplorerService {

    /**
     * Method used to load from a path. It will create new artiste, new album,
     * new music if they don't already exist
     *
     * @param rootPath
     * @throws Exception
     */
    IntegrationDto loadMusics();

}
