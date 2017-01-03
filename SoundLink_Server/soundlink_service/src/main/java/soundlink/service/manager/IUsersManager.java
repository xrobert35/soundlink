package soundlink.service.manager;

import soundlink.dto.UsersDto;
import soundlink.model.entities.Users;

public interface IUsersManager {

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

}
