package soundlink.service.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import soundlink.model.entities.Playlist;

@Transactional
public interface IPlaylistManager extends ISoundlinkManager<Playlist, Integer> {

    /**
     * Get all user's playlist
     * 
     * @param userId
     * @return
     */
    List<Playlist> getPlaylistsByUserId(Integer userId);

}
