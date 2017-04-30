package soundlink.service.manager.impl;

import java.util.List;

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

    @Override
    public Users create(Users user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Users update(Users user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Users user) {
        userRepository.delete(user);
    }

    @Override
    public Users findOne(Integer key) {
        return userRepository.findOne(key);
    }

    @Override
    public Users getOne(Integer key) {
        return userRepository.getOne(key);
    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public Users getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Users updateUser(String login, UsersDto usersDto) {
        Users user = userRepository.findByLogin(login);
        user.setPicture(usersDto.getPicture());
        user.setEmail(usersDto.getEmail());
        if (usersDto.getNewPassword() != null) {
            user.setPassword(usersDto.getNewPassword());
        }
        return userRepository.save(user);
    }

    @Override
    public Users getUserWithFavoriteArtistesFetchByLogin(String login) {
        return userRepository.getUserWithFavoriteArtistesFetchByLogin(login);
    }

    @Override
    public Users getUserWithFavoriteAlbumsFetchByLogin(String login) {
        return userRepository.getUserWithFavoriteAlbumsFetchByLogin(login);
    }

}
