package soundlink.service.business;

/**
 * Service used to explore music files
 *
 * @author xrobert
 *
 */
public interface MusicExplorerService {

    /**
     * Method used to load from a path. It will create new artiste, new album,
     * new music if they don't already exist
     *
     * @param rootPath
     * @throws Exception
     */
    void loadMusicsFromPath(String rootPath) throws Exception;

}
