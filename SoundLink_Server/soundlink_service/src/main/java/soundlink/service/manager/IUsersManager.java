package soundlink.service.manager;

import org.springframework.transaction.annotation.Transactional;

import soundlink.dto.UsersDto;
import soundlink.model.entities.Users;

@Transactional
public interface IUsersManager extends ISoundlinkManager<Users, Integer> {

    /**
     * Allow you to get user information by is login
     * 
     * @param login
     * @return
     */
    Users getUserByLogin(String login);

    /**
     * Allow you to update user information
     * 
     * @param name
     * @param usersDto
     * @return
     */
    Users updateUser(String name, UsersDto usersDto);

    /**
     * Get user with favorite artistes fetched
     * 
     * @return
     */
    Users getUserWithFavoriteArtistesFetchByLogin(String login);

    /**
     * Get user with favorite albums fetched
     * 
     * @param login
     * @return
     */
    Users getUserWithFavoriteAlbumsFetchByLogin(String login);

}
