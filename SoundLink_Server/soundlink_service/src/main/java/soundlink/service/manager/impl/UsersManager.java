package soundlink.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.UserRepository;
import soundlink.dto.UsersDto;
import soundlink.model.entities.Users;
import soundlink.service.manager.IUsersManager;

/**
 * This class is used to manage users
 * 
 * @author xrobert
 *
 */
@Service
public class UsersManager implements IUsersManager {

    @Autowired
    private UserRepository userRepository;

    public Users getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Users updateUser(String login, UsersDto usersDto) {
        Users user = userRepository.findByLogin(login);
        user.setPicture(usersDto.getPicture());
        user.setEmail(usersDto.getEmail());

        return userRepository.save(user);
    }

}
